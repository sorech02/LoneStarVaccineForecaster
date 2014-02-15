package org.tch.forecast.core.logic;

import org.tch.forecast.core.ImmunizationForecastDataBean;
import org.tch.forecast.core.Trace;

public class TransitionScheduleStep extends ActionStep
{
  public static final String NAME = "Transition Schedule";

  @Override
  public String getName()
  {
    return NAME;
  }

  @Override
  public String doAction(DataStore ds) throws Exception
  {
    ds.log("Transition Schedule");
    ds.log(" + Setting current schedule to the new one indicated");
    ds.schedule = ds.forecast.getSchedules().get(ds.nextAction);
    ds.indicatesPos = -1;
    ds.previousAfterInvalidInterval = null;
    if (ds.schedule == null)
    {
      throw new Exception("Unable to find schedule " + ds.nextAction);
    }
    if (ds.traceBuffer != null)
    {
      String label = ds.schedule.getLabel();
      if (label == null || label.equals(""))
      {
        label = "[Schedule " + ds.schedule.getScheduleName() + "]";
      }
      ds.traceBuffer.append("Now expecting " + label + " dose.</li><li>");
    }
    if (ds.trace != null)
    {
      ds.trace = new Trace();
      ds.trace.setSchedule(ds.schedule);
      ds.traceList.add(ds.trace);
      String label = ds.schedule.getLabel();
      if (label == null || label.equals(""))
      {
        label = "[Schedule " + ds.schedule.getScheduleName() + "]";
      }
      ds.traceList.append("Now expecting " + label + " dose.</li><li>");
    }
    ds.log("Checking finished date");
    ds.finished = ds.schedule.getFinishedAge().getDateTimeFrom(ds.patient.getDobDateTime());
    if (ds.today.isGreaterThan(ds.finished))
    {
      ds.log(" + Patient is now too old to get any more doses");
      if (ds.traceBuffer != null)
      {
        ds.traceBuffer.append("No need for further vaccinations after " + ds.finished.toString("YMD") + ".");
      }
      if (ds.trace != null)
      {
        ds.trace.setFinished(true);
        ds.traceList.append("No need for further vaccinations after " + ds.finished.toString("YMD") + ".");
      }
      ImmunizationForecastDataBean forecastBean = new ImmunizationForecastDataBean();
      forecastBean.setFinished(ds.finished.getDate());
      forecastBean.setForecastName(ds.forecast.getForecastCode());
      forecastBean.setForecastLabel(ds.forecast.getForecastLabel());
      forecastBean.setSortOrder(ds.forecast.getSortOrder());
      forecastBean.setImmregid(ds.patient.getImmregid());
      forecastBean.setTraceList(ds.traceList);
      forecastBean.setStatusDescription(ImmunizationForecastDataBean.STATUS_DESCRIPTION_FINISHED);
      ds.resultList.add(forecastBean);
      return FinishScheduleStep.NAME;
    }
    ds.log("Going to next step");
    LookForDoseStep.setHasEvent(ds);
    return TraverseScheduleStep.NAME;
  }

}
