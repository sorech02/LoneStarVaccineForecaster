package org.tch.forecast.core;

import org.tch.hl7.core.util.DateTime;

public interface PatientForecastRecordDataBean
{
  public int getImmregid();
  public String getSex();
  public DateTime getDobDateTime();
}