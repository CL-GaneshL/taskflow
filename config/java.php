<?php

namespace Config;

return [
    /*
      |--------------------------------------------------------------------------
      | the java jdk home path
      |--------------------------------------------------------------------------
      |
     */
     'JAVA_HOME' => env('JAVA_HOME', base_path('java'))
//    'JAVA_HOME' => env('JAVA_HOME', base_path('resources/java/jdk/') . 'jdk1.8.0_73')
];

