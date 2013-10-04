package org.tch.forecast.core.server;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.tch.forecast.core.DateTime;
import org.tch.forecast.core.ImmunizationForecastDataBean;
import org.tch.forecast.core.ImmunizationInterface;
import org.tch.forecast.core.SoftwareVersion;
import org.tch.forecast.core.api.impl.CvxCodes;
import org.tch.forecast.core.api.impl.VaccineForecastManager;
import org.tch.forecast.core.model.ImmunizationMDA;

public class CaretForecaster
{

  public static final String FORECASTING_MODE_ACCEPTABLE = "A";
  public static final String FORECASTING_MODE_RECOMMENDED = "R";

  public static final String HL7_CODE_ERROR_CODE_NONE = "0";
  public static final String HL7_CODE_ERROR_CODE_UNRECOGNIZED = "1";
  public static final String HL7_CODE_ERROR_CODE_UNSUPPORTED = "2";

  // public static final int FIELD_IN_01_LOG_ERRRORS = 1;
  public static final int FIELD_IN_02_DATE_USED_FOR_FORECAST = 2; // USE
  // public static final int FIELD_IN_03_FORECAST_DOSES_DUE = 3;
  // public static final int FIELD_IN_04_FORECAST_DOSES_PAST_DUE = 4;
  // public static final int FIELD_IN_05_FORECAST_DOSES_DUE_NEXT = 5;
  // public static final int FIELD_IN_06_PERFORM_AGE_TEST_SCREENING = 6;
  // public static final int FIELD_IN_07_PERFORM_INTERVAL_TEST_SCREENING = 7;
  // public static final int FIELD_IN_08_PERFORM_LIVE_VACCINE_TEST_SCREENING = 8;
  public static final int FIELD_IN_09_FORECASTING_MODE = 9;
  // public static final int FIELD_IN_10_NUMBERED_DOSE_PROCESSING_FLAG = 10;
  // public static final int FIELD_IN_11_INVALID_VALID_DOSE_FORECASTING_INTERVAL = 11;
  // public static final int FIELD_IN_12_HEPB_SERIES_ACTIVATION_FLAG = 12;
  // public static final int FIELD_IN_13_DTP_SERIES_ACTIVATION_FLAG = 13;
  // public static final int FIELD_IN_14_TD_BOOSTER_SERIES_ACTIVATION_FLAG = 14;
  // public static final int FIELD_IN_15_HIB_SERIES_ACTIVATION_FLAG = 15;
  // public static final int FIELD_IN_16_POLIO_SERIES_ACTIVATION_FLAG = 16;
  // public static final int FIELD_IN_17_MMR_SERIES_ACTIVATION_FLAG = 17;
  // public static final int FIELD_IN_18_HEPA_SERIES_ACTIVATION_FLAG = 18;
  // public static final int FIELD_IN_19_VARICELLA_SERIES_ACTIVATION_FLAG = 19;
  // public static final int FIELD_IN_20_ROTAVIRUS_SERIES_ACTIVATIO_FLAG = 20;
  // public static final int FIELD_IN_21_STREP_PNEUMOCOCCAL_SERIES_ACTIVATION_FLAG = 21;
  // public static final int FIELD_IN_22_INFLUENZA_SERIES_ACTIVATION_FLAG = 22;
  // public static final int FIELD_IN_23_MENINGOCOCCAL_SERIES_ACTIVATION_FLAG = 23;
  // public static final int FIELD_IN_24_HPV_SERIES_ACTIVATION_FLAG = 24;
  // public static final int FIELD_IN_25_H1N1FLUE_SERIES_ACTIVATION_FLAG = 25;
  public static final int FIELD_IN_26_VERSION = 26;
  // public static final int FIELD_IN_27_FORECASTING_DAYS_MONTH = 27;
  // public static final int FIELD_IN_28_RETROSPECTIVE_ANALYSIS_DAYS_MONTHS = 28;
  public static final int FIELD_IN_29_PERSONAL_ID = 29;
  public static final int FIELD_IN_30_USER_NOTE = 30;
  public static final int FIELD_IN_31_DATE_OF_BIRTH = 31;
  public static final int FIELD_IN_32_GENDER = 32;

  public static final int FIELD_IN_33_MOTHER_HBSAG_STATUS = 33; // TODO
  public static final int FIELD_IN_34_PERTUSSIS_CONTRAINDICATED_INDICATION = 34;// TODO
  public static final int FIELD_IN_35_DIPHTHERIA_CONTRAINDICATED_INDICATION = 35;// TODO
  public static final int FIELD_IN_36_TETANUS_CONTRAINDICATED_INDICATION = 36;// TODO
  public static final int FIELD_IN_37_HIB_CONTRAINDICATED_INDICATION = 37;// TODO
  public static final int FIELD_IN_38_HBIG_CONTRAINDICATED_INDICATION = 39;
  public static final int FIELD_IN_40_OPV_CONTRAINDICATED_INDICATION = 40;
  public static final int FIELD_IN_41_IPV_CONTRAINDICATED_INDICATION = 41;// TODO
  public static final int FIELD_IN_42_MEASLES_CONTRAINDICATED_INDICATION = 42;// TODO
  public static final int FIELD_IN_43_MUMPS_CONTRAINDICATED_INDICATION = 43;// TODO
  public static final int FIELD_IN_44_RUBELLA_CONTRAINDICATED_INDICATION = 44;// TODO
  public static final int FIELD_IN_45_VARICELLA_CONTRAINDICATED_INDICATION = 45;// TODO
  public static final int FIELD_IN_46_HEPA_CONTRAINDICATED_INDICATION = 46;// TODO
  public static final int FIELD_IN_47_RV_CONTRAINDICATED_INDICATION = 47;// TODO
  public static final int FIELD_IN_48_S_PN_CONTRAINDICATED_INDICATION = 48;// TODO
  public static final int FIELD_IN_49_CONTRAINDICATED_INDICATION = 49;// TODO
  public static final int FIELD_IN_50_MENINGOCOCCAL_CONTRAINDICATED_INDICATION = 50;// TODO
  public static final int FIELD_IN_51_HPV_CONTRAINDICATED_INDICATION = 51;// TODO
  public static final int FIELD_IN_52_H1N1_CONTRAINDICATED_INDICATION = 52;

  public static final int FIELD_IN_53_NUMBER_OF_INPUT_DOSES = 53;

  public static final int FIELD_IN_54_DOSE_NOTE = 1;
  public static final int FIELD_IN_55_DOSE_HL7_CODE = 2;
  // public static final int FIELD_IN_56_DOSE_NUMBER = 3;
  // public static final int
  // FIELD_IN_57_DOSE_NUMBER_FOR_THE_SECOND_COMPONENT_OF_A_COMBINATION_VACCINE =
  // 4;
  // public static final int
  // FIELD_IN_58_DOSE_NUMBER_FOR_THE_THIRD_COMPONENT_OF_A_COMBINATION_VACCINE =
  // 5;
  public static final int FIELD_IN_59_DATE_OF_DOSE_ADMINISTRATION = 6; // USE
  public static final int FIELD_IN_60_DOSE_OVERRIDE = 7;
  public static final int FIELD_IN_61_RESERVED_FOR_FUTURE_USE = 8;
  public static final int FIELD_IN_62_RESERVED_FOR_FUTURE_USE = 9;

  // ++ Out fields +++++++++++++++++++++++++++++++++++++
  public static final int FIELD_OUT_ERROR = 0;
  public static final int FIELD_OUT_01_COPYRIGHT_NOTICE = 1;
  public static final int FIELD_OUT_02_RUN_DATE_AND_TIME = 2;
  // public static final int FIELD_OUT_03_RUN_CODE = 3;
  public static final int FIELD_OUT_04_DATE_USED_FOR_FORECAST_ = 4;
  // public static final int FIELD_OUT_05_DATE_USED_FOR_FORECAST_ERROR_CODE = 5;
  // public static final int FIELD_OUT_06_FORECAST_DOSES_DUE = 6;
  // public static final int FIELD_OUT_07_FORECAST_DOSES_DUE_ERROR_CODE = 7;
  // public static final int FIELD_OUT_08_FORECAST_DOSES_PAST_DUE = 8;
  // public static final int FIELD_OUT_09_FORECAST_DOSES_PAST_DUE_ERROR_CODE = 9;
  // public static final int FIELD_OUT_10_FORECAST_DOSES_DUE_NEXT = 10;
  // public static final int FIELD_OUT_11_FORECAST_DOSES_DUE_NEXT_ERROR_CODE = 11;
  // public static final int FIELD_OUT_12_PERFORM_AGE_TEST_SCREENING = 12;
  // public static final int FIELD_OUT_13_PERFORM_AGE_TEST_SCREENING_ERROR_CODE = 13;
  // public static final int FIELD_OUT_14_PERFORM_INTERVAL_TEST_SCREENING = 14;
  // public static final int FIELD_OUT_15_PERFORM_INTERVAL_TEST_SCREENING_ERROR_CODE = 15;
  // public static final int FIELD_OUT_16_PERFORM_LIVE_VACCINE_TEST_SCREENING = 16;
  // public static final int FIELD_OUT_17_PERFORM_LIVE_VACCINE_TEST_SCREENING_ERROR_CODE = 17;
  public static final int FIELD_OUT_18_FORECASTING_MODE = 18;
  // public static final int FIELD_OUT_19_FORECASTING_MODE_ERROR_CODE = 19;
  // public static final int FIELD_OUT_20_INVALID_VALID_DOSE_FORECASTING_INTERVAL = 20;
  // public static final int FIELD_OUT_21_INVALID_VALID_DOSE_FORECASTING_INTERVAL_ERROR_CODE = 21;
  // public static final int FIELD_OUT_22_NUMBERED_DOSE_PROCESSING_FLAG = 22;
  // public static final int FIELD_OUT_23_NUMBERED_DOSE_PROCESSING_FLAG_ERROR_CODE = 23;
  // public static final int FIELD_OUT_24_HEPB_SERIES_ACTIVATION_FLAG = 24;
  // public static final int FIELD_OUT_25_HEPB_SERIES_CANDO = 25;
  // public static final int FIELD_OUT_26_DTP_SERIES_ACTIVATION_FLAG = 26;
  // public static final int FIELD_OUT_27_DTP_SERIES_CANDO = 27;
  // public static final int FIELD_OUT_28_TD_B_SERIES_ACTIVATION_FLAG = 28;
  // public static final int FIELD_OUT_29_TD_B_SERIES_CANDO = 29;
  // public static final int FIELD_OUT_30_HIB_SERIES_ACTIVATION_FLAG = 30;
  // public static final int FIELD_OUT_31_HIB_SERIES_CANDO = 31;
  // public static final int FIELD_OUT_32_POLIO_SERIES_ACTIVATION_FLAG = 32;
  // public static final int FIELD_OUT_33_POLIO_SERIES_CANDO = 33;
  // public static final int FIELD_OUT_34_MMR_SERIES_ACTIVATION_FLAG = 34;
  // public static final int FIELD_OUT_35_MMR_SERIES_CANDO = 35;
  // public static final int FIELD_OUT_36_HEPA_SERIES_ACTIVATION_FLAG = 36;
  // public static final int FIELD_OUT_37_HEPA_SERIES_CANDO = 37;
  // public static final int FIELD_OUT_38_VARICELLA_SERIES_ACTIVATION_FLAG = 38;
  // public static final int FIELD_OUT_39_VARICELLA_SERIES_CANDO = 39;
  // public static final int FIELD_OUT_40_ROTAVIRUS_SERIES_ACTIVATION_FLAG = 40;
  // public static final int FIELD_OUT_41_ROTAVIRUS_SERIES_CANDO = 41;
  // public static final int FIELD_OUT_42_STREP_PNEUMOCOCCAL_SERIES_ACTIVATION_FLAG = 42;
  // public static final int FIELD_OUT_43_STREP_PNEUMOCOCCAL_SERIES_CANDO = 43;
  // public static final int FIELD_OUT_44_INFLUENZA_SERIES_ACTIVATION_FLAG = 44;
  // public static final int FIELD_OUT_45_INFLUENZA_SERIES_CANDO = 45;
  // public static final int FIELD_OUT_46_MENINGOCOCCAL_SERIES_ACTIVATION_FLAG = 46;
  // public static final int FIELD_OUT_47_MENINGOCOCCAL_SERIES_CANDO = 47;
  // public static final int FIELD_OUT_48_HPV_SERIES_ACTIVATION_FLAG = 48;
  // public static final int FIELD_OUT_49_HPV_SERIES_CANDO = 49;
  // public static final int FIELD_OUT_50_H1N1FLU_SERIES_ACTIVATION_FLAG = 50;
  // public static final int FIELD_OUT_51_H1N1FLU_SERIES_CANDO = 51;
  public static final int FIELD_OUT_52_VERSION = 52;
  //  public static final int FIELD_OUT_53_VERSION_ERROR_CODE = 53;
  //  public static final int FIELD_OUT_54_RULE_SET_MAJOR_VERSION = 54;
  //  public static final int FIELD_OUT_55_RULE_SET_MINOR_VERSION = 55;
  //  public static final int FIELD_OUT_56_RULE_SET_RELEASE_DATE = 56;
  //  public static final int FIELD_OUT_57_IMM_SERVE_LIBRARY_MAJOR_VERSION = 57;
  //  public static final int FIELD_OUT_58_IMM_SERVE_LIBRARY_MINOR_VERSION = 58;
  //  public static final int FIELD_OUT_59_IMM_SERVE_LIBRARY_RELEASE_DATE = 59;
  //  public static final int FIELD_OUT_60_FORECAST_DAYS_MONTH = 60;
  //  public static final int FIELD_OUT_61_FORECAST_ANALYSIS_DAYS_MONTH_ERROR_CODE = 61;
  //  public static final int FIELD_OUT_62_RETROSPECTIVE_ANALYSIS_DAYS_MONTH = 62;
  //  public static final int FIELD_OUT_63_RETROSPECTIVE_ANALYSIS_DAYS_MONTH_ERROR_CODE = 63;
  public static final int FIELD_OUT_64_PERSONAL_ID = 64;
  public static final int FIELD_OUT_65_USER_NOTE = 65;
  public static final int FIELD_OUT_66_DATE_OF_BIRTH = 66;
  //  public static final int FIELD_OUT_67_DATE_OF_BIRTH_ERROR_CODE = 67;
  public static final int FIELD_OUT_68_GENDER = 68;
  //  public static final int FIELD_OUT_69_MOTHER_HBSAG_STATUS = 69;
  //  public static final int FIELD_OUT_70_MOTHER_HBSAG_STATUS_ERROR_CODE = 70;
  //  public static final int FIELD_OUT_71_PERTUSSIS_CONTRAINDICATED_INDICATION = 71;
  //  public static final int FIELD_OUT_72_PERTUSSIS_CONTRAINDICATED_INDICATION_ERROR_CODE = 72;
  //  public static final int FIELD_OUT_73_DIPHTHERIA_CONTRAINDICATED_INDICATION = 73;
  //  public static final int FIELD_OUT_74_DIPHTHERIA_CONTRAINDICATED_INDICATION_ERROR_CODE = 74;
  //  public static final int FIELD_OUT_75_TETANUS_CONTRAINDICATED_INDICATION = 75;
  //  public static final int FIELD_OUT_76_TETANUS_CONTRAINDICATED_INDICATION_ERROR_CODE = 76;
  //  public static final int FIELD_OUT_77_HIB_CONTRAINDICATED_INDICATION = 77;
  //  public static final int FIELD_OUT_78_HIB_CONTRAINDICATED_INDICATION_ERROR_CODE = 78;
  //  public static final int FIELD_OUT_79_HBIG_CONTRAINDICATED_INDICATION = 79;
  //  public static final int FIELD_OUT_80_HBIG_CONTRAINDICATED_INDICATION_ERROR_CODE = 80;
  //  public static final int FIELD_OUT_81_HEPB_CONTRAINDICATED_INDICATION = 81;
  //  public static final int FIELD_OUT_82_HEPB_CONTRAINDICATED_INDICATION_ERROR_CODE = 82;
  //  public static final int FIELD_OUT_83_OPV_CONTRAINDICATED_INDICATION = 83;
  //  public static final int FIELD_OUT_84_OPV_CONTRAINDICATED_INDICATION_ERROR_CODE = 84;
  //  public static final int FIELD_OUT_85_IPV_CONTRAINDICATED_INDICATION = 85;
  //  public static final int FIELD_OUT_86_IPV_CONTRAINDICATED_INDICATION_ERROR_CODE = 86;
  //  public static final int FIELD_OUT_87_MEASLES_CONTRAINDICATED_INDICATION = 87;
  //  public static final int FIELD_OUT_88_MEASLES_CONTRAINDICATED_INDICATION_ERROR_CODE = 88;
  //  public static final int FIELD_OUT_89_MUMPS_CONTRAINDICATED_INDICATION = 89;
  //  public static final int FIELD_OUT_90_MUMPS_CONTRAINDICATED_INDICATION_ERROR_CODE = 90;
  //  public static final int FIELD_OUT_91_RUBELLA_CONTRAINDICATED_INDICATION = 91;
  //  public static final int FIELD_OUT_92_RUBELLA_CONTRAINDICATED_INDICATION_ERROR_CODE = 92;
  //  public static final int FIELD_OUT_93_VARICELLA_CONTRAINDICATED_INDICATION = 93;
  //  public static final int FIELD_OUT_94_VARICELLA_CONTRAINDICATED_INDICATION_ERROR_CODE = 94;
  //  public static final int FIELD_OUT_95_HEPA_CONTRAINDICATED_INDICATION = 95;
  //  public static final int FIELD_OUT_96_HEPA_CONTRAINDICATED_INDICATION_ERROR_CODE = 96;
  //  public static final int FIELD_OUT_97_ROTAVIRUS_CONTRAINDICATED_INDICATION = 97;
  //  public static final int FIELD_OUT_98_ROTAVIRUS_CONTRAINDICATED_INDICATION_ERROR_CODE = 98;
  //  public static final int FIELD_OUT_99_STREP_PNEUMOCOCCAL_CONTRAINDICATED_INDICATION = 99;
  //  public static final int FIELD_OUT_100_STREP_PNEUMOCOCCAL_CONTRAINDICATED_INDICATION_ERROR_CODE = 100;
  //  public static final int FIELD_OUT_101_INFLUENZA_CONTRAINDICATED_INDICATION = 101;
  //  public static final int FIELD_OUT_102_INFLUENZA_CONTRAINDICATED_INDICATION_ERROR_CODE = 102;
  //  public static final int FIELD_OUT_103_MENINGOCOCCAL_CONTRAINDICATED_INDICATION = 103;
  //  public static final int FIELD_OUT_104_MENINGOCOCCAL_CONTRAINDICATED_INDICATION_ERROR_CODE = 104;
  //  public static final int FIELD_OUT_105_HPV_CONTRAINDICATED_INDICATION = 105;
  //  public static final int FIELD_OUT_106_HPVCONTRAINDICATED_INDICATION_ERROR_CODE = 106;
  //  public static final int FIELD_OUT_107_H1N1FLU_CONTRAINDICATED_INDICATION = 107;
  //  public static final int FIELD_OUT_108_H1N1FLU_CONTRAINDICATED_INDICATION_ERROR_CODE = 108;
  public static final int FIELD_OUT_109_NUMBER_OF_INPUT_DOSES = 109;
  //  public static final int FIELD_OUT_110_NUMBER_OF_INPUT_DOSES_ERROR_CODE = 110;
  public static final int FIELD_OUT_DOSE_BASE = 110;
  public static final int FIELD_OUT_DOSE_BASE_ADD = 27;
  public static final int FIELD_OUT_111_DOSE_NOTE = 1;
  public static final int FIELD_OUT_112_DOSE_INPUT_HL7_CODE = 2;
  public static final int FIELD_OUT_113_DOSE_INPUT_HL7_CODE_ERROR_CODE = 3;
  public static final int FIELD_OUT_114_DOSE_INPUT_PRINT_STRING = 4;
  public static final int FIELD_OUT_115_DOSE_HL7_CODE_ = 5;
  public static final int FIELD_OUT_116_DOSE_HL7_CODE_PRINT_STRING = 6;
  //  public static final int FIELD_OUT_117_DOSE_NUMBER = 7;
  //  public static final int FIELD_OUT_118_DOSE_NUMBER_ERROR = 8;
  //  public static final int FIELD_OUT_119_DOSE_NUMBER_SOURCE = 9;
  //  public static final int FIELD_OUT_120_DOSE_NUMBER_SOURCE_ERROR_CODE = 10;
  public static final int FIELD_OUT_121_DATE_OF_DOSE_ADMINISTRATION = 11;
  //  public static final int FIELD_OUT_122_DATE_OF_DOSE_ADMINISTRATION_ERROR_CODE = 12;
  public static final int FIELD_OUT_123_DOSE_OVERRIDE = 13;
  //  public static final int FIELD_OUT_124_DOSE_OVERRIDE_ERROR_CODE = 14;
  //  public static final int FIELD_OUT_125_DOSE_AGE_SCREENING_TEST_RESULT = 15;
  //  public static final int FIELD_OUT_126_DOSE_AGE_SCREENING_TEST_UNITS = 16;
  //  public static final int FIELD_OUT_127_DOSE_AGE_SCREENING_TEST_VALUE = 17;
  //  public static final int FIELD_OUT_128_DOSE_INTERVAL_SCREENING_TEST_RESULT = 18;
  //  public static final int FIELD_OUT_129_CONFLICTING_DOSE_TYPE = 19;
  //  public static final int FIELD_OUT_130_CONFLICTING_DOSE_ADMINISTRATION_DATE = 20;
  //  public static final int FIELD_OUT_131_DOSE_INTERVAL_SCREENING_TEST_UNITS = 21;
  //  public static final int FIELD_OUT_132_DOSE_INTERVAL_SCREENING_TEST_VALUE = 22;
  //  public static final int FIELD_OUT_133_DOSE_LIVE_VACCINE_SCREENING_TEST_RESULT = 23;
  //  public static final int FIELD_OUT_134_CONFLICTING_LIVE_VACCINE_DOSE_TYPE = 24;
  //  public static final int FIELD_OUT_135_CONFLICTING_LIVE_VACCINE_DOSE_ADMINISTRATION_DATE = 25;
  //  public static final int FIELD_OUT_136_DOSE_LIVE_VACCINE_SCREENING_TEST_UNITS = 26;
  //  public static final int FIELD_OUT_137_DOSE_LIVE_VACCINE_SCREENING_TEST_VALUE = 27;
  public static final int FIELD_OUT_138_NUMBER_OF_DOSES_DUE_ON_THE_DATE_USED_FOR_FORECAST = 28;
  public static final int FIELD_OUT_DOSE_DUE_BASE_ADD = 12;
  public static final int FIELD_OUT_139_DOSE_DUE_IMM_SERVE_SERIES_CODE = 1;
  // public static final int FIELD_OUT_140_DOSE_DUE_DOSE_HL7_CODE = 2;
  //  public static final int FIELD_OUT_141_DOSE_DUE_HL7_CODE_PRINT_STRING = 3;
  public static final int FIELD_OUT_142_DOSE_DUE_DOSE_NUMBER = 4;
  public static final int FIELD_OUT_143_DOSE_DUE_PAST_DUE_INDICATOR = 5;
  public static final int FIELD_OUT_144_DOSE_DUE_MINIMUM_DATE = 6;
  public static final int FIELD_OUT_145_DOSE_DUE_RECOMMENDED_DATE = 7;
  public static final int FIELD_OUT_146_DOSE_DUE_EXCEEDS_DATE = 8;
  //  public static final int FIELD_OUT_147_DOSE_DUE_MINIMUM_REMINDER_DATE = 9;
  //  public static final int FIELD_OUT_148_DOSE_DUE_RECOMMENDED_REMINDER_DATE = 10;
  //  public static final int FIELD_OUT_149_DOSE_DUE_EXCEEDS_REMINDER_DATE = 11;
  //  public static final int FIELD_OUT_150_DOSE_DUE_VFC_PAYMENT_INDICATOR = 12;
  public static final int FIELD_OUT_151_NUMBER_OF_DOSES_DUE_NEXT = 13;
  public static final int FIELD_OUT_DOSE_DUE_NEXT_BASE_ADD = 9;
  public static final int FIELD_OUT_152_DOSE_DUE_NEXT_IMM_SERVE_SERIES_CODE = 1;
  //  public static final int FIELD_OUT_153_DOSE_DUE_NEXT_DOSE_HL7_CODE = 2;
  //  public static final int FIELD_OUT_154_DOSE_DUE_NEXT_HL7_CODE_PRINT_STRING = 3;
  public static final int FIELD_OUT_155_DOSE_DUE_NEXT_DOSE_NUMBER = 4;
  public static final int FIELD_OUT_156_DOSE_DUE_NEXT_DEPENDENT_DOSE_INDEX = 5;
  public static final int FIELD_OUT_157_DOSE_DUE_NEXT_ACCEPTABLE_ADMINISTRATION_DATE_ = 6;
  public static final int FIELD_OUT_158_DOSE_DUE_NEXT_RECOMMENDED_ADMINISTRATION_DATE = 7;
  public static final int FIELD_OUT_159_DOSE_DUE_NEXT_EXCEEDS_DATE_ = 8;
  // public static final int FIELD_OUT_160_DOSE_DUE_NEXT_VFC_PAYMENT_INDICATOR = 9;
  public static final int FIELD_OUT_161_HIB_SERIES_COMPLETED_INDICATOR = 10;
  public static final int FIELD_OUT_162_HEPATITIS_A_SERIES_COMPLETED_INDICATOR = 11;
  public static final int FIELD_OUT_163_HEPATITIS_B_SERIES_COMPLETED_INDICATOR = 12;
  public static final int FIELD_OUT_164_PRIMARY_DTP_SERIES_COMPLETED_INDICATOR = 13;
  public static final int FIELD_OUT_165_POLIO_VACCINE_SERIES_COMPLETED_INDICATOR = 14;
  public static final int FIELD_OUT_166_MMR_SERIES_COMPLETED_INDICATOR = 15;
  public static final int FIELD_OUT_167_VARICELLA_SERIES_COMPLETED_INDICATOR = 16;
  public static final int FIELD_OUT_168_RV_SERIES_COMPLETED_INDICATOR = 17;
  public static final int FIELD_OUT_169_STREP_PNEUMOCOCCAL_SERIES_COMPLETED_INDICATOR = 18;
  public static final int FIELD_OUT_170_MENINGOCOCCAL_SERIES_COMPLETED_INDICATOR = 19;
  public static final int FIELD_OUT_171_HPV_SERIES_COMPLETED_INDICATOR = 20;
  //  public static final int FIELD_OUT_172_H1N1FLU_SERIES_COMPLETED_INDICATOR = 21;
  //  public static final int FIELD_OUT_173_DTP_PRIOR_SPECIAL_NOTE = 22;
  //  public static final int FIELD_OUT_174_TD_B_WITH_INCOMPLETE_DTP_SERIES_SPECIAL_NOTE = 23;
  //  public static final int FIELD_OUT_175_HEPB_SCREENING_SPECIAL_NOTE = 24;
  //  public static final int FIELD_OUT_176_DTORP_SCREENING_SPECIAL_NOTE = 25;
  //  public static final int FIELD_OUT_177_HIB_SCREENING_SPECIAL_NOTE = 26;
  //  public static final int FIELD_OUT_178_POLIO_SCREENING_SPECIAL_NOTE = 27;
  //  public static final int FIELD_OUT_179_MMR_SCREENING_SPECIAL_NOTE = 28;
  //  public static final int FIELD_OUT_180_VAR_SCREENING_SPECIAL_NOTE = 29;
  //  public static final int FIELD_OUT_181_HEPA_SCREENING_SPECIAL_NOTE = 30;
  //  public static final int FIELD_OUT_182_ROTAVIRUS_SCREENING_SPECIAL_NOTE = 31;
  //  public static final int FIELD_OUT_183_STREP_PNEUMOCOCCAL_SCREENING_SPECIAL_NOTE = 32;
  //  public static final int FIELD_OUT_184_INFLUENZA_SCREENING_SPECIAL_NOTE = 33;
  //  public static final int FIELD_OUT_185_MENINGOCOCCAL_SCREENING_SPECIAL_NOTE = 34;
  //  public static final int FIELD_OUT_186_HPVSCREENING_SPECIAL_NOTE = 35;
  //  public static final int FIELD_OUT_187_HPV_SCREENING_SPECIAL_NOTE = 36;
  //  public static final int FIELD_OUT_188_H1N1FLU_SCREENING_SPECIAL_NOTE = 37;
  //  public static final int FIELD_OUT_189_HEPB4_REQUIRED_SPECIAL_NOTE = 38;
  //  public static final int FIELD_OUT_190_MMR_SHUTDOWN_NOTICE = 39;
  //  public static final int FIELD_OUT_191_DTP_6_DOSE_CAVEAT_NOTICE = 40;
  //  public static final int FIELD_OUT_192_MENINGOCOCCAL_PRIOR_SPECIAL_NOTE = 41;
  //  public static final int FIELD_OUT_193_MENINGOCOCCAL_SUPPLY_ADVISORY = 42;
  //  public static final int FIELD_OUT_194_TWO_INFLUENZA_DOSES_DUE_IN_2011_12_SEASON_VDH_ADVISORY = 43;
  public static final int FIELD_OUT_HUMAN_READABLE_LOG = 0; //TODO 

  private int runCode = 0;

  private String request = "";
  private String[] fields = null;
  private StringBuilder response = new StringBuilder();
  private int currentPosition = 1;

  private static HashMap<String, String> seriesOutHash = new HashMap<String, String>();
  static {
    seriesOutHash.put(ImmunizationForecastDataBean.DTAP, "101"); // D/T Series
    seriesOutHash.put(ImmunizationForecastDataBean.DIPHTHERIA, "101"); // D/T Series
    seriesOutHash.put(ImmunizationForecastDataBean.TDAP, "101"); // D/T Series
    seriesOutHash.put(ImmunizationForecastDataBean.HEPA, "102"); // Hepatitis A Series
    seriesOutHash.put(ImmunizationForecastDataBean.HEPB, "103"); // Hepatitis B Series
    seriesOutHash.put(ImmunizationForecastDataBean.HIB, "104"); // Hib Series
    seriesOutHash.put(ImmunizationForecastDataBean.MMR, "105"); // MMR Series
    seriesOutHash.put(ImmunizationForecastDataBean.MEASLES, "105"); // MMR Series
    seriesOutHash.put(ImmunizationForecastDataBean.MUMPS, "105"); // MMR Series
    seriesOutHash.put(ImmunizationForecastDataBean.RUBELLA, "105"); // MMR  Series
    seriesOutHash.put(ImmunizationForecastDataBean.POLIO, "106"); // IPV/OPV  Series
    seriesOutHash.put(ImmunizationForecastDataBean.VARICELLA, "107"); // Varicella
    // seriesOutHash.put("", "108"); // HBIG -- IMM/Serve treats HBIG as a standalone series
    seriesOutHash.put(ImmunizationForecastDataBean.TD, "109"); // D/T Series
    seriesOutHash.put(ImmunizationForecastDataBean.ROTAVIRUS, "110"); // Rotavirus Series
    seriesOutHash.put(ImmunizationForecastDataBean.PNEUMO, "111"); // Strep-Pneumococcal Series
    seriesOutHash.put(ImmunizationForecastDataBean.INFLUENZA, "112"); // Influenza Series
    seriesOutHash.put(ImmunizationForecastDataBean.MENING, "113"); // Meningococcal Series
    seriesOutHash.put(ImmunizationForecastDataBean.HPV, "114"); // HPV Series
    // seriesOutHash.put("", "115"); // H1N1Flu Series
  }
  private static HashMap<String, String> doseDueOutHash = new HashMap<String, String>();
  static {
    // doseDueOutHash.put(ImmunizationForecastDataBean. ,"1"); // DTP
    // doseDueOutHash.put(ImmunizationForecastDataBean. ,"2"); // OPV
    doseDueOutHash.put(ImmunizationForecastDataBean.MMR, "3"); // MMR
    doseDueOutHash.put(ImmunizationForecastDataBean.MEASLES, "5"); // Measles
    doseDueOutHash.put(ImmunizationForecastDataBean.HEPB, "8"); // Hep B adolescent or pediatric
    doseDueOutHash.put(ImmunizationForecastDataBean.TD, "9"); // Td adult IPV
    doseDueOutHash.put(ImmunizationForecastDataBean.HIB, "17"); // Hib  unspecified
    doseDueOutHash.put(ImmunizationForecastDataBean.DTAP, "20"); // DTaP
    doseDueOutHash.put(ImmunizationForecastDataBean.VARICELLA, "21"); // Varicella
    // doseDueOutHash.put(ImmunizationForecastDataBean. ,"28"); // DT pediatric
    // doseDueOutHash.put(ImmunizationForecastDataBean. ,"30"); // HBIG
    doseDueOutHash.put(ImmunizationForecastDataBean.HEPA, "31"); // Hep A  pediatric
    doseDueOutHash.put(ImmunizationForecastDataBean.PNEUMO, "33"); // Strep  Pneumococcal (polysacchoride)
    // doseDueOutHash.put(ImmunizationForecastDataBean. ,"49"); // Hib PRP-OMP
    doseDueOutHash.put(ImmunizationForecastDataBean.HPV, "62"); // HPV,  quadrivalent
    // doseDueOutHash.put(ImmunizationForecastDataBean. ,"89"); // Unspecified Polio
    doseDueOutHash.put(ImmunizationForecastDataBean.MENING, "114"); // Meningococcal (MCV4)
    doseDueOutHash.put(ImmunizationForecastDataBean.TDAP, "115"); // Tdap
    // doseDueOutHash.put(ImmunizationForecastDataBean. ,"116"); // Rotavirus, pentavalent
    // doseDueOutHash.put(ImmunizationForecastDataBean. ,"119"); // Rotavirus,monovalent
    doseDueOutHash.put(ImmunizationForecastDataBean.ROTAVIRUS, "122"); // Rotavirus, NOS
    // doseDueOutHash.put(ImmunizationForecastDataBean. ,"128"); // H1N1-09, NOS
    doseDueOutHash.put(ImmunizationForecastDataBean.PNEUMO, "133"); // Pneumococcal,  PCV13
    doseDueOutHash.put(ImmunizationForecastDataBean.INFLUENZA, "141"); // Influenza, seasonal, injectable
    // doseDueOutHash.put(ImmunizationForecastDataBean. ,"-10"); // Td Adult  Booster
    // doseDueOutHash.put(ImmunizationForecastDataBean. ,"-12"); // Unspecified D/T
    // doseDueOutHash.put(ImmunizationForecastDataBean. ,"-13"); // Tdap Adult Booster

  }

  public CaretForecaster(String request) throws Exception {
    this.request = request;

    List<String> fieldList = new ArrayList<String>();
    StringBuilder sb = new StringBuilder();
    for (char c : request.toCharArray()) {
      if (c == '^') {
        fieldList.add(sb.toString());
        sb.setLength(0);
      } else {
        sb.append(c);
      }
    }
    fieldList.add(sb.toString());
    fields = (String[]) fieldList.toArray(new String[] {});

    if (fields.length < FIELD_IN_53_NUMBER_OF_INPUT_DOSES) {
      runCode = -19;
    }

    // Set all fields to empty
    for (int i = 0; i < fields.length; i++) {
      if (fields[i] == null || fields[i].equals("NULL")) {
        fields[i] = "";
      }
    }
  }

  public String forecast(VaccineForecastManager vaccineForecastManager, Map<String, Integer> cvxToVaccineIdMap)
      throws Exception {

    // TODO ERROR Code &&& Text &&& Description
    if (runCode < 0) {
      response.append("runCode = " + runCode);
      // do error segment
    } else {

      Date forecastDate = readDate(FIELD_IN_02_DATE_USED_FOR_FORECAST);
      Date dateOfBirth = readDate(FIELD_IN_31_DATE_OF_BIRTH);
      String gender = readField(FIELD_IN_32_GENDER);

      ForecastRunner forecastRunner = new ForecastRunner(vaccineForecastManager);
      forecastRunner.getPatient().setDob(new DateTime(dateOfBirth));
      forecastRunner.getPatient().setSex(gender.toUpperCase().startsWith("M") ? "M" : "F");
      forecastRunner.setForecastDate(forecastDate);
      List<ImmunizationInterface> imms = forecastRunner.getImms();

      int numberOfDoses = readInt(FIELD_IN_53_NUMBER_OF_INPUT_DOSES);
      for (int i = 0; i < numberOfDoses; i++) {
        int offset = FIELD_IN_53_NUMBER_OF_INPUT_DOSES + (i * 7);
        String doseNote = readField(FIELD_IN_54_DOSE_NOTE + offset);
        String cvxCode = readField(FIELD_IN_55_DOSE_HL7_CODE + offset);
        // TODO mapping code to cvx
        // for now, tack on 0 for single digit codes
        if (cvxCode.length() == 1) {
          cvxCode = "0" + cvxCode;
        }
        Integer vaccineId = null;

        if (cvxToVaccineIdMap.containsKey(cvxCode)) {
          vaccineId = cvxToVaccineIdMap.get(cvxCode);
        }

        Date doseAdminDate = readDate(FIELD_IN_59_DATE_OF_DOSE_ADMINISTRATION + offset);

        ImmunizationMDA imm = new ImmunizationMDA();
        imm.setCvx(cvxCode);
        imm.setDateOfShot(doseAdminDate);
        imm.setDoseNote(doseNote);
        if (vaccineId == null) {
          imm.setHl7CodeErrorCode(HL7_CODE_ERROR_CODE_UNSUPPORTED);
          imm.setVaccineId(0);
        } else {
          imm.setHl7CodeErrorCode("0");
          imm.setVaccineId(vaccineId);
        }
        imms.add(imm);

      }

      forecastRunner.forecast();

      DateTime today = new DateTime();

      String forecastingMode = readField(FIELD_IN_09_FORECASTING_MODE);
      if (!forecastingMode.equals(FORECASTING_MODE_ACCEPTABLE)) {
        forecastingMode = FORECASTING_MODE_RECOMMENDED;
      }

      // Put together response
      addValue("TCH Forecaster version " + SoftwareVersion.VERSION, FIELD_OUT_01_COPYRIGHT_NOTICE);
      addValue(today.toString("YMDHTS"), FIELD_OUT_02_RUN_DATE_AND_TIME);
      //  addValue(Integer.toString(runCode), FIELD_OUT_03_RUN_CODE);
      addValue((new DateTime(forecastDate)).toString("YMD"), FIELD_OUT_04_DATE_USED_FOR_FORECAST_);
      // addValue("0", FIELD_OUT_05_DATE_USED_FOR_FORECAST_ERROR_CODE);
      // addValue("0", FIELD_OUT_07_FORECAST_DOSES_DUE_ERROR_CODE);
      // addValue("0", FIELD_OUT_09_FORECAST_DOSES_PAST_DUE_ERROR_CODE);
      // addValue("0", FIELD_OUT_11_FORECAST_DOSES_DUE_NEXT_ERROR_CODE);
      // addValue("0", FIELD_OUT_13_PERFORM_AGE_TEST_SCREENING_ERROR_CODE);
      // addValue("0", FIELD_OUT_15_PERFORM_INTERVAL_TEST_SCREENING_ERROR_CODE);
      // addValue("0", FIELD_OUT_17_PERFORM_LIVE_VACCINE_TEST_SCREENING_ERROR_CODE);
      addValue(forecastingMode, FIELD_OUT_18_FORECASTING_MODE);
      //      addValue("0", FIELD_OUT_19_FORECASTING_MODE_ERROR_CODE);
      //      addValue("0", FIELD_OUT_21_INVALID_VALID_DOSE_FORECASTING_INTERVAL_ERROR_CODE);
      //      addValue("0", FIELD_OUT_23_NUMBERED_DOSE_PROCESSING_FLAG_ERROR_CODE);
      //      addValue("0", FIELD_OUT_25_HEPB_SERIES_CANDO);
      //      addValue("0", FIELD_OUT_27_DTP_SERIES_CANDO);
      //      addValue("0", FIELD_OUT_29_TD_B_SERIES_CANDO);
      //      addValue("0", FIELD_OUT_31_HIB_SERIES_CANDO);
      //      addValue("0", FIELD_OUT_33_POLIO_SERIES_CANDO);
      //      addValue("0", FIELD_OUT_35_MMR_SERIES_CANDO);
      //      addValue("0", FIELD_OUT_37_HEPA_SERIES_CANDO);
      //      addValue("0", FIELD_OUT_39_VARICELLA_SERIES_CANDO);
      //      addValue("0", FIELD_OUT_41_ROTAVIRUS_SERIES_CANDO);
      //      addValue("0", FIELD_OUT_43_STREP_PNEUMOCOCCAL_SERIES_CANDO);
      //      addValue("0", FIELD_OUT_45_INFLUENZA_SERIES_CANDO);
      //      addValue("0", FIELD_OUT_47_MENINGOCOCCAL_SERIES_CANDO);
      //      addValue("0", FIELD_OUT_49_HPV_SERIES_CANDO);
      //      addValue("0", FIELD_OUT_51_H1N1FLU_SERIES_CANDO);
      addValue(readField(FIELD_IN_26_VERSION), FIELD_OUT_52_VERSION);
      //      addValue("0", FIELD_OUT_53_VERSION_ERROR_CODE);
      //      addValue(SoftwareVersion.VERSION, FIELD_OUT_54_RULE_SET_MAJOR_VERSION);
      //      addValue(vaccineForecastManager.getForecastSchedule().getScheduleName(), FIELD_OUT_55_RULE_SET_MINOR_VERSION);
      //      addValue(SoftwareVersion.VERSION_RELEASE, FIELD_OUT_56_RULE_SET_RELEASE_DATE);
      //      addValue(SoftwareVersion.VERSION_MAJOR, FIELD_OUT_57_IMM_SERVE_LIBRARY_MAJOR_VERSION);
      //      addValue(SoftwareVersion.VERSION_MINOR, FIELD_OUT_58_IMM_SERVE_LIBRARY_MINOR_VERSION);
      //      addValue(SoftwareVersion.VERSION_RELEASE, FIELD_OUT_59_IMM_SERVE_LIBRARY_RELEASE_DATE);
      //      addValue("0", FIELD_OUT_61_FORECAST_ANALYSIS_DAYS_MONTH_ERROR_CODE);
      //      addValue("0", FIELD_OUT_63_RETROSPECTIVE_ANALYSIS_DAYS_MONTH_ERROR_CODE);
      addValue(readField(FIELD_IN_29_PERSONAL_ID), FIELD_OUT_64_PERSONAL_ID);
      addValue(readField(FIELD_IN_30_USER_NOTE), FIELD_OUT_65_USER_NOTE);
      addValue(readField(FIELD_IN_31_DATE_OF_BIRTH), FIELD_OUT_66_DATE_OF_BIRTH);
      addValue(readField(FIELD_IN_32_GENDER), FIELD_OUT_68_GENDER);
      //      addValue("0", FIELD_OUT_70_MOTHER_HBSAG_STATUS_ERROR_CODE);
      //      addValue("0", FIELD_OUT_72_PERTUSSIS_CONTRAINDICATED_INDICATION_ERROR_CODE);
      //      addValue("0", FIELD_OUT_74_DIPHTHERIA_CONTRAINDICATED_INDICATION_ERROR_CODE);
      //      addValue("0", FIELD_OUT_76_TETANUS_CONTRAINDICATED_INDICATION_ERROR_CODE);
      //      addValue("0", FIELD_OUT_78_HIB_CONTRAINDICATED_INDICATION_ERROR_CODE);
      //      addValue("0", FIELD_OUT_80_HBIG_CONTRAINDICATED_INDICATION_ERROR_CODE);
      //      addValue("0", FIELD_OUT_82_HEPB_CONTRAINDICATED_INDICATION_ERROR_CODE);
      //      addValue("0", FIELD_OUT_84_OPV_CONTRAINDICATED_INDICATION_ERROR_CODE);
      //      addValue("0", FIELD_OUT_86_IPV_CONTRAINDICATED_INDICATION_ERROR_CODE);
      //      addValue("0", FIELD_OUT_88_MEASLES_CONTRAINDICATED_INDICATION_ERROR_CODE);
      //      addValue("0", FIELD_OUT_90_MUMPS_CONTRAINDICATED_INDICATION_ERROR_CODE);
      //      addValue("0", FIELD_OUT_92_RUBELLA_CONTRAINDICATED_INDICATION_ERROR_CODE);
      //      addValue("0", FIELD_OUT_94_VARICELLA_CONTRAINDICATED_INDICATION_ERROR_CODE);
      //      addValue("0", FIELD_OUT_96_HEPA_CONTRAINDICATED_INDICATION_ERROR_CODE);
      //      addValue("0", FIELD_OUT_98_ROTAVIRUS_CONTRAINDICATED_INDICATION_ERROR_CODE);
      //      addValue("0", FIELD_OUT_100_STREP_PNEUMOCOCCAL_CONTRAINDICATED_INDICATION_ERROR_CODE);
      //      addValue("0", FIELD_OUT_102_INFLUENZA_CONTRAINDICATED_INDICATION_ERROR_CODE);
      //      addValue("0", FIELD_OUT_104_MENINGOCOCCAL_CONTRAINDICATED_INDICATION_ERROR_CODE);
      //      addValue("0", FIELD_OUT_106_HPVCONTRAINDICATED_INDICATION_ERROR_CODE);
      //      addValue("0", FIELD_OUT_108_H1N1FLU_CONTRAINDICATED_INDICATION_ERROR_CODE);
      addValue(readField(FIELD_IN_53_NUMBER_OF_INPUT_DOSES), FIELD_OUT_109_NUMBER_OF_INPUT_DOSES);
      //      addValue("0", FIELD_OUT_110_NUMBER_OF_INPUT_DOSES_ERROR_CODE);

      int base = FIELD_OUT_DOSE_BASE;
      for (int i = 0; i < imms.size(); i++) {
        ImmunizationMDA imm = (ImmunizationMDA) imms.get(i);
        addValue(imm.getDoseNote(), FIELD_OUT_111_DOSE_NOTE + base);
        addValue(imm.getCvx(), FIELD_OUT_112_DOSE_INPUT_HL7_CODE + base);
        addValue(imm.getDoseNote(), FIELD_OUT_113_DOSE_INPUT_HL7_CODE_ERROR_CODE + base);
        addValue(imm.getCvx(), FIELD_OUT_114_DOSE_INPUT_PRINT_STRING + base);
        addValue(imm.getCvx(), FIELD_OUT_115_DOSE_HL7_CODE_ + base);
        if (imm.getVaccineId() != 0) {
          addValue(imm.getCvx(), FIELD_OUT_116_DOSE_HL7_CODE_PRINT_STRING + base);
        }
        //        addValue("0", FIELD_OUT_117_DOSE_NUMBER + base);
        //        addValue("0", FIELD_OUT_119_DOSE_NUMBER_SOURCE + base);
        addValue(new DateTime(imm.getDateOfShot()).toString("YMD"), FIELD_OUT_121_DATE_OF_DOSE_ADMINISTRATION + base);
        //        addValue("0", FIELD_OUT_124_DOSE_OVERRIDE_ERROR_CODE + base);
        //        addValue("0", FIELD_OUT_125_DOSE_AGE_SCREENING_TEST_RESULT + base);
        //        addValue("0", FIELD_OUT_128_DOSE_INTERVAL_SCREENING_TEST_RESULT + base);
        base += FIELD_OUT_DOSE_BASE_ADD;
      }

      Set<String> nc = new HashSet<String>();

      List<ImmunizationForecastDataBean> forecastListDueToday = forecastRunner.getForecastListDueToday();
      for (Iterator<ImmunizationForecastDataBean> it = forecastListDueToday.iterator(); it.hasNext();) {
        ImmunizationForecastDataBean forecastResult = it.next();
        nc.add(forecastResult.getForecastName());
        if (!seriesOutHash.containsKey(forecastResult.getForecastName())) {
          it.remove();
        }
      }
      base++;
      addValue(String.valueOf(forecastListDueToday.size()), base);
      for (ImmunizationForecastDataBean forecastResult : forecastListDueToday) {
        String doseDueCode = seriesOutHash.get(forecastResult.getForecastName());
        //        String doseHL7Code = doseDueOutHash.get(forecastResult.getForecastName());
        //        if (doseHL7Code == null) {
        //          doseHL7Code = "";
        //        }
        addValue(doseDueCode, FIELD_OUT_139_DOSE_DUE_IMM_SERVE_SERIES_CODE + base);
        // addValue(doseHL7Code, FIELD_OUT_140_DOSE_DUE_DOSE_HL7_CODE + base);
        // addValue(forecastResult.getForecastLabel(), FIELD_OUT_141_DOSE_DUE_HL7_CODE_PRINT_STRING + base);
        addValue(forecastResult.getDose(), FIELD_OUT_142_DOSE_DUE_DOSE_NUMBER + base);
        boolean overdue = forecastResult.getStatusDescription().equals("overdue");
        addValue(overdue ? "1" : "0", FIELD_OUT_143_DOSE_DUE_PAST_DUE_INDICATOR + base);
        addValue(d(forecastResult.getValid()), FIELD_OUT_144_DOSE_DUE_MINIMUM_DATE + base);
        addValue(d(forecastResult.getDue()), FIELD_OUT_145_DOSE_DUE_RECOMMENDED_DATE + base);
        if (forecastingMode.equals(FORECASTING_MODE_ACCEPTABLE)) {
          addValue(d(forecastResult.getDue()), FIELD_OUT_144_DOSE_DUE_MINIMUM_DATE + base);
        } else {
          addValue(d(forecastResult.getValid()), FIELD_OUT_144_DOSE_DUE_MINIMUM_DATE + base);
        }
        addValue(d(forecastResult.getOverdue()), FIELD_OUT_146_DOSE_DUE_EXCEEDS_DATE + base);
        //        addValue(d(forecastResult.getOverdue()), FIELD_OUT_147_DOSE_DUE_MINIMUM_REMINDER_DATE + base);
        //        addValue(d(forecastResult.getOverdue()), FIELD_OUT_148_DOSE_DUE_RECOMMENDED_REMINDER_DATE + base);
        //        addValue(overdue ? "1" : "0", FIELD_OUT_149_DOSE_DUE_EXCEEDS_REMINDER_DATE + base);
        //        addValue("", FIELD_OUT_150_DOSE_DUE_VFC_PAYMENT_INDICATOR + base);
        base += FIELD_OUT_DOSE_DUE_BASE_ADD;
      }
      List<ImmunizationForecastDataBean> forecastListDueLater = forecastRunner.getForecastListDueLater();
      for (Iterator<ImmunizationForecastDataBean> it = forecastListDueLater.iterator(); it.hasNext();) {
        ImmunizationForecastDataBean forecastResult = it.next();
        nc.add(forecastResult.getForecastName());
        if (!seriesOutHash.containsKey(forecastResult.getForecastName())) {
          it.remove();
        }
      }
      base++;
      addValue(String.valueOf(forecastListDueLater.size()), base);
      for (ImmunizationForecastDataBean forecastResult : forecastListDueLater) {
        String doseDueCode = seriesOutHash.get(forecastResult.getForecastName());
        //        String doseHL7Code = doseDueOutHash.get(forecastResult.getForecastName());
        //        if (doseHL7Code == null) {
        //          doseHL7Code = "";
        //        }
        addValue(doseDueCode, FIELD_OUT_152_DOSE_DUE_NEXT_IMM_SERVE_SERIES_CODE + base);
        //        addValue(doseHL7Code, FIELD_OUT_153_DOSE_DUE_NEXT_DOSE_HL7_CODE + base);
        //        addValue(forecastResult.getForecastLabel(), FIELD_OUT_154_DOSE_DUE_NEXT_HL7_CODE_PRINT_STRING + base);
        addValue(forecastResult.getDose(), FIELD_OUT_155_DOSE_DUE_NEXT_DOSE_NUMBER + base);
        addValue("", FIELD_OUT_156_DOSE_DUE_NEXT_DEPENDENT_DOSE_INDEX + base);
        addValue(d(forecastResult.getValid()), FIELD_OUT_157_DOSE_DUE_NEXT_ACCEPTABLE_ADMINISTRATION_DATE_ + base);
        addValue(d(forecastResult.getDue()), FIELD_OUT_158_DOSE_DUE_NEXT_RECOMMENDED_ADMINISTRATION_DATE + base);
        addValue(d(forecastResult.getOverdue()), FIELD_OUT_159_DOSE_DUE_NEXT_EXCEEDS_DATE_ + base);
        //        addValue("", FIELD_OUT_160_DOSE_DUE_NEXT_VFC_PAYMENT_INDICATOR + base);
        base += FIELD_OUT_DOSE_DUE_NEXT_BASE_ADD;
      }
      addValue(c(nc, ImmunizationForecastDataBean.HIB), FIELD_OUT_161_HIB_SERIES_COMPLETED_INDICATOR + base);
      addValue(c(nc, ImmunizationForecastDataBean.HEPA), FIELD_OUT_162_HEPATITIS_A_SERIES_COMPLETED_INDICATOR + base);
      addValue(c(nc, ImmunizationForecastDataBean.HEPB), FIELD_OUT_163_HEPATITIS_B_SERIES_COMPLETED_INDICATOR + base);
      addValue(c(nc, ImmunizationForecastDataBean.DTAP), FIELD_OUT_164_PRIMARY_DTP_SERIES_COMPLETED_INDICATOR + base);
      addValue(c(nc, ImmunizationForecastDataBean.POLIO), FIELD_OUT_165_POLIO_VACCINE_SERIES_COMPLETED_INDICATOR + base);
      addValue(c(nc, ImmunizationForecastDataBean.MMR), FIELD_OUT_166_MMR_SERIES_COMPLETED_INDICATOR + base);
      addValue(c(nc, ImmunizationForecastDataBean.VARICELLA), FIELD_OUT_167_VARICELLA_SERIES_COMPLETED_INDICATOR + base);
      addValue(c(nc, ImmunizationForecastDataBean.ROTAVIRUS), FIELD_OUT_168_RV_SERIES_COMPLETED_INDICATOR + base);
      addValue(c(nc, ImmunizationForecastDataBean.PNEUMO), FIELD_OUT_169_STREP_PNEUMOCOCCAL_SERIES_COMPLETED_INDICATOR
          + base);
      addValue(c(nc, ImmunizationForecastDataBean.MENING), FIELD_OUT_170_MENINGOCOCCAL_SERIES_COMPLETED_INDICATOR
          + base);
      addValue(c(nc, ImmunizationForecastDataBean.HPV), FIELD_OUT_171_HPV_SERIES_COMPLETED_INDICATOR + base);

    }

    return response.toString();
  }

  public String c(Set<String> notCompleted, String forecastName) {
    return notCompleted.contains(forecastName) ? "0" : "1";
  }

  private SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy");

  private int readInt(int position) throws Exception {
    String value = readField(position);
    if (value.equals("")) {
      return 0;
    }
    return Integer.parseInt(value);
  }

  private String d(Date d) {
    return new DateTime(d).toString("YMD");
  }

  private Date readDate(int position) throws Exception {
    String value = readField(position);
    if (value.equals("")) {
      value = "07041776";
    }
    return sdf.parse(value);
  }

  private String readField(int position) {
    position--;
    if (position < fields.length) {
      String value = fields[position];
      if (value.equalsIgnoreCase("NULL")) {
        return "";
      }
      return value.trim();
    }
    return "";
  }

  private void addValue(String value, int position) {
    if (position < currentPosition) {
      throw new IllegalArgumentException("Unable to add value to output in position " + position
          + ", already at position " + currentPosition);
    }
    while (currentPosition < position) {
      response.append("^");
      currentPosition++;
    }
    response.append(value);
    response.append("^");
    currentPosition++;
  }

  // java -classpath tch-forecaster.jar
  // org.tch.forecast.core.server.CaretForecaster

  public static void main(String[] args) throws Exception {
    String request = ForecastServer.TEST_1;
    if (args.length > 0) {
      request = args[0];
    }
    CaretForecaster cf = new CaretForecaster(request);
    VaccineForecastManager vaccineForecastManager = new VaccineForecastManager();
    Map<String, Integer> cvxToVaccineIdMap = CvxCodes.getCvxToVaccineIdMap();
    String response = cf.forecast(vaccineForecastManager, cvxToVaccineIdMap);
    System.out.println(response);
  }
}
