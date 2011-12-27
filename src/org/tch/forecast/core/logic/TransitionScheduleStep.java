package org.tch.forecast.core.logic;

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
    // A different schedule is the new action
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
    ds.finished = ds.schedule.getFinishedAge().getDateTimeFrom(ds.patient.getDobDateTime());
    if (ds.today.isGreaterThan(ds.finished))
    {
      if (ds.traceBuffer != null)
      {
        ds.traceBuffer.append("</li><li>No need for further vaccinations.");
      }
      if (ds.trace != null)
      {
        ds.trace.setFinished(true);
        ds.traceList.append("</li><li>No need for further vaccinations.");
      }
      return FinishScheduleStep.NAME;
    }
    return TraverseScheduleStep.NAME;
  }

}