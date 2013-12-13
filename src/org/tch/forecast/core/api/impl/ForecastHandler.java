package org.tch.forecast.core.api.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.tch.forecast.core.DateTime;
import org.tch.forecast.core.ImmunizationForecastDataBean;
import org.tch.forecast.core.ImmunizationInterface;
import org.tch.forecast.core.VaccinationDoseDataBean;
import org.tch.forecast.core.api.model.ForecastHandlerInterface;
import org.tch.forecast.core.api.model.ForecastPatientInterface;
import org.tch.forecast.core.api.model.ForecastRecommendationInterface;
import org.tch.forecast.core.api.model.ForecastRequestInterface;
import org.tch.forecast.core.api.model.ForecastResponseInterface;
import org.tch.forecast.core.api.model.ForecastVaccinationInterface;
import org.tch.forecast.core.model.Immunization;
import org.tch.forecast.core.model.PatientRecordDataBean;

public class ForecastHandler implements ForecastHandlerInterface {

  private static Map<String, Integer> cvxToVaccineIdMap = null;

  public static Map<String, Integer> getCvxToVaccineIdMap() throws Exception {
    if (cvxToVaccineIdMap == null) {
      cvxToVaccineIdMap = CvxCodes.getCvxToVaccineIdMap();
    }
    return cvxToVaccineIdMap;
  }

  private synchronized void initCvxCodes() throws Exception {
    if (cvxToVaccineIdMap == null) {
      cvxToVaccineIdMap = CvxCodes.getCvxToVaccineIdMap();
    }
  }
 

  private static ForecastHandlerCore forecastHandlerCore = null;

  public ForecastHandler() throws Exception {
    initCvxCodes();
  }

  public ForecastResponseInterface forecast(ForecastRequestInterface forecastRequest) throws Exception {

    ForecastResponseInterface forecastResponse = new ForecastResponse();

    List<VaccinationDoseDataBean> doseList = new ArrayList<VaccinationDoseDataBean>();
    PatientRecordDataBean patient = new PatientRecordDataBean();
    List<ImmunizationInterface> imms = new ArrayList<ImmunizationInterface>();

    DateTime forecastDate = new DateTime();
    if (forecastRequest.getEvaluationDate() != null) {
      forecastDate = new DateTime(forecastRequest.getEvaluationDate());
    }

    ForecastPatientInterface forecastPatient = forecastRequest.getPatient();
    patient.setDob(new DateTime(forecastPatient.getBirthDate()));
    patient.setSex(forecastPatient.getSex().toUpperCase());
    for (ForecastVaccinationInterface forecastVaccination : forecastRequest.getVaccinationList()) {

      String vaccineCvx = forecastVaccination.getCvxCode();
      String vaccineMvx = forecastVaccination.getMvxCode();
      int vaccineId = 0;
      if (vaccineCvx == null) {
        throw new Exception("CVX code not indicated, required field");
      } else {
        if (!cvxToVaccineIdMap.containsKey(vaccineCvx) && !cvxToVaccineIdMap.containsKey("0" + vaccineCvx)) {
          throw new Exception("CVX code '" + vaccineCvx + "' is not recognized");
        }
        if (cvxToVaccineIdMap.containsKey(vaccineCvx)) {
          vaccineId = cvxToVaccineIdMap.get(vaccineCvx);
        } else {
          vaccineId = cvxToVaccineIdMap.get("0" + vaccineCvx);
        }
        if (vaccineId == 0) {
          throw new Exception("CVX code '" + vaccineCvx + "' is not recognized");
        }
      }
      Immunization imm = new Immunization();
      imm.setCvx(vaccineCvx);
      imm.setDateOfShot(forecastVaccination.getAdminDate());
      imm.setVaccineId(vaccineId);
      imm.setMvx(vaccineMvx);
      imm.setVaccinationId(forecastVaccination.getVaccinationId());
      imms.add(imm);
    }
    
    ForecastOptions forecastOptions = new ForecastOptions();

    Map traceMap = new HashMap();
    List<ImmunizationForecastDataBean> resultList = new ArrayList<ImmunizationForecastDataBean>();
    VaccineForecastManager vaccineForecastManager = new VaccineForecastManager();
    ForecastHandlerCore forecastHandlerCore = new ForecastHandlerCore(vaccineForecastManager);
    String forecasterScheduleName = forecastHandlerCore.forecast(doseList, patient, imms, forecastDate, traceMap,
        resultList, forecastOptions);

    forecastResponse.setEvaluationSchedule(forecasterScheduleName);

    List<ForecastRecommendationInterface> forecastRecommendationList = new ArrayList<ForecastRecommendationInterface>();
    forecastResponse.setRecommendationList(forecastRecommendationList);

    {

      traceMap.remove(ImmunizationForecastDataBean.PERTUSSIS);

      for (Iterator<ImmunizationForecastDataBean> it = resultList.iterator(); it.hasNext();) {
        ImmunizationForecastDataBean forecast = it.next();
        ForecastRecommendationInterface forecastRecommendation = new ForecastRecommendation();

        setStatusDescription(forecastDate, forecast, forecastRecommendation);

        
        forecastRecommendation.setAntigenName(forecast.getForecastNameOriginal());
        forecastRecommendation.setDisplayLabel(forecast.getForecastLabel());
        forecastRecommendation.setDoseNumber(forecast.getDose());
        forecastRecommendation.setDueDate(forecast.getDue());
        forecastRecommendation.setValidDate(forecast.getValid());
        forecastRecommendation.setOverdueDate(forecast.getOverdue());
        forecastRecommendation.setFinishedDate(forecast.getFinished());
        forecastRecommendation.setDecisionProcessTextHTML(forecast.getTraceList().getExplanation().toString());

        forecastRecommendationList.add(forecastRecommendation);
      }
    }
    List<ForecastVaccinationInterface> forecastVaccinationList = new ArrayList<ForecastVaccinationInterface>();
    for (VaccinationDoseDataBean dose : doseList) {
      ForecastVaccinationInterface fv = new ForecastVaccination();
      fv.setAdminDate(dose.getAdminDate());
      fv.setCvxCode(dose.getCvxCode());
      fv.setMvxCode(dose.getMvxCode());
      fv.setDoseCode(dose.getDoseCode());
      fv.setForecastCode(dose.getForecastCode());
      fv.setReasonText(dose.getReason());
      fv.setWhenValidText(dose.getWhenValidText());
      fv.setScheduleCode(dose.getScheduleCode());
      fv.setTchCode(String.valueOf(dose.getVaccineId()));
      fv.setVaccinationId(dose.getVaccinationId());
      fv.setStatusCode(dose.getStatusCode());
      forecastVaccinationList.add(fv);
    }
    forecastResponse.setVaccinationList(forecastVaccinationList);
    return forecastResponse;
  }

  private void setStatusDescription(DateTime forecastDate, ImmunizationForecastDataBean forecast,
      ForecastRecommendationInterface forecastRecommendation) {
    DateTime dueDate = new DateTime(forecast.getDue());
    DateTime overdueDate = new DateTime(forecast.getOverdue());
    DateTime finishedDate = new DateTime(forecast.getFinished());
    if (forecastDate.isLessThan(dueDate)) {
      forecastRecommendation.setStatusDescription("");
    } else if (forecastDate.isLessThan(overdueDate)) {
      forecastRecommendation.setStatusDescription("due");
    } else if (forecastDate.isLessThan(finishedDate)) {
      forecastRecommendation.setStatusDescription("overdue");
    } else {
      forecastRecommendation.setStatusDescription("finished");
    }
  }

}
