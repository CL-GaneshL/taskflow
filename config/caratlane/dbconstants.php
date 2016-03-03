<?php

namespace Config;

/*
  |==========================================================================
  | Caratlane TaskFlow DB constants
  |==========================================================================
 */

return [
    /*
      |--------------------------------------------------------------------------
      | Http error codes
      |--------------------------------------------------------------------------
     */
    'HTTP_OK' => 200,
    'HTTP_INVALID_CREDENTIALS_ERROR' => 401,
    'HTTP_INVALID_CREDENTIALS_ERROR_MSG' => 'Invalid Credentials',
    'HTTP_INTERNAL_SERVER_ERROR' => 500,
    'HTTP_INTERNAL_SERVER_ERROR_MSG' => 'Internal Server Error',
    /*
      |--------------------------------------------------------------------------
      | Table Skills
      |--------------------------------------------------------------------------
     */
    'TASKFLOW_SKILLS_REFERENCE_MAX_SIZE' => 7,
    'TASKFLOW_SKILLS_DESIGNATION_MAX_SIZE' => 40,
];

