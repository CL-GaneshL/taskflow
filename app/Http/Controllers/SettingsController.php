<?php

namespace App\Http\Controllers;

use Config;
use App\Http\Controllers\Controller;
use App\Http\Controllers\SettingsHelper;
use Symfony\Component\Process\Process;

class SettingsController extends Controller {

    private $HTTP_OK = null;
    private $HTTP_INTERNAL_SERVER_ERROR = null;
    private $HTTP_INTERNAL_SERVER_ERROR_MSG = null;

//    private $buffer = null;

    public function __construct() {

        $this->HTTP_OK = Config::get('caratlane.dbconstants.HTTP_OK');
        $this->HTTP_INTERNAL_SERVER_ERROR = Config::get('caratlane.dbconstants.HTTP_INTERNAL_SERVER_ERROR');
        $this->HTTP_INTERNAL_SERVER_ERROR_MSG = Config::get('caratlane.dbconstants.HTTP_INTERNAL_SERVER_ERROR_MSG');

        // -------------------------------------
        // Apply the jwt.auth middleware to all methods in this controller
        // -------------------------------------
        $this->middleware('jwt.auth');

//        $this->buffer = array();
    }

//    private function getDebugMode() {
//
//        $mode = Config::get('app.debug');
//        if ($mode === true) {
//            $mode = 'debug';
//        } else {
//            $mode = '';
//        }
//
//        return $mode;
//    }
//
//    private function getJavaBinary() {
//
//        $java_bin_dir = Config::get('java.JAVA_HOME') . DIRECTORY_SEPARATOR . 'bin';
//        $java_bin = '"' . $java_bin_dir . DIRECTORY_SEPARATOR . 'java' . '"';
//
//        return $java_bin;
//    }
//
//    private function getJarFile() {
//
//        $jar_path = base_path('resources/bin');
//        $jar_file = Config::get('caratlane.constants.TASK_GENERATOR_JAR');
//        $jar = '"' . $jar_path . DIRECTORY_SEPARATOR . $jar_file . '"';
//
//        return $jar;
//    }
//
//    private function getLogPath() {
//
//        $log_path = storage_path('logs');
//        $log_file = Config::get('caratlane.constants.TASK_GENERATOR_LOG_FILE');
//        $logpath = '"' . $log_path . DIRECTORY_SEPARATOR . $log_file . '"';
//
//        return $logpath;
//    }
//
//    private function getDbHost() {
//
//        $default = Config::get('database.default');
//        $config = Config::get('database.connections.' . $default);
//
//        return $config['host'];
//    }
//
//    private function getDbPort() {
//
//        $default = Config::get('database.default');
//        $config = Config::get('database.connections.' . $default);
//
//        return $config['port'];
//    }
//
//    private function getDatabase() {
//
//        $default = Config::get('database.default');
//        $config = Config::get('database.connections.' . $default);
//
//        return $config['database'];
//    }
//
//    private function getDbUsername() {
//
//        $default = Config::get('database.default');
//        $config = Config::get('database.connections.' . $default);
//
//        return $config['username'];
//    }
//
//    private function getDbPassword() {
//
//        $default = Config::get('database.default');
//        $config = Config::get('database.connections.' . $default);
//
//        return $config['password'];
//    }
//
//    private function getCommandLine($action) {
//
//        $commandline = '';
//        $commandline = $commandline . ' -host=' . $this->getDbHost();
//        $commandline = $commandline . ' -port=' . $this->getDbPort();
//        $commandline = $commandline . ' -database=' . $this->getDatabase();
//        $commandline = $commandline . ' -username=' . $this->getDbUsername();
//        $commandline = $commandline . ' -password=' . $this->getDbPassword();
//        $commandline = $commandline . ' -logpath=' . $this->getLogPath();
//        $commandline = $commandline . ' -mode=' . $this->getDebugMode();
//        $commandline = $commandline . ' -action=' . $action;
//
//        return $commandline;
//    }

    /**
     * 
     */
    public function getTaskflowConfiguration() {

        $settings = new SettingsHelper();

        $settings->log('');
        $settings->log('............................');
        $settings->log('TaskGenerator configuration :');

        $settings->log('   - debug mode    : [' . $settings->getDebugMode() . ']');
        $settings->log('   - java binary   : [' . $settings->getJavaBinary() . ']');
        $settings->log('   - jar file      : [' . $settings->getJarFile() . ']');
        $settings->log('   - log path      : [' . $settings->getLogPath() . ']');
        $settings->log('   - db host       : [' . $settings->getDbHost() . ']');
        $settings->log('   - db port       : [' . $settings->getDbPort() . ']');
        $settings->log('   - database      : [' . $settings->getDatabase() . ']');
        $settings->log('   - db username   : [' . $settings->getDbUsername() . ']');
        $settings->log('   - db password   : [' . $settings->getDbPassword() . ']');
        $settings->log('   - command line  : [' . $settings->getCommandLine("") . ']');

        $action = 'version';
        $java_bin = $settings->getJavaBinary();
        $jar = $settings->getJarFile();
        $commandline = $settings->getCommandLine($action);
        $command = $java_bin . ' -jar ' . $jar . ' ' . $commandline;

        $process = new Process($command);
        $process->start();

        $settings->log("TaskGenerator version : ");
        $process->wait(function ($type, $buffer)use ($settings) {
            $str = str_replace("\r\n", "", $buffer);
            if (strlen($str) != 0) {
                $settings->log($str);
            }
        });

        $settings->log('--');

        return $settings->publish("TaskGenerator configuration.");
    }

    /**
     * 
     */
    public function getJavaConfiguration() {

        $settings = new SettingsHelper();

        $settings->log('');
        $settings->log('............................');
        $settings->log('TaskGenerator Java version :');

        $process = new Process($settings->getJavaBinary() . ' ' . '-version');
        $process->start();
        $process->wait(function ($type, $buffer)use ($settings) {

            $str = str_replace("\r\n", "", $buffer);
            if (strlen($str) != 0) {
                $settings->log($str);
            }
        });

        $settings->log('--');

        return $settings->publish("TaskGenerator Java version.");
    }

    /**
     * 
     * @param type $str
     */
//    private function log($str) {
//        $this->buffer [] = $str;
//    }

    /**
     * 
     * @return type
     */
//    private function publish($msg) {
//
//        // create a temporary buffer
//        // this buffer will be return the the client
//        // while the original buffer is destroy
//        $response_buffer = array();
//
//        // trace the msg in the log file
//        $max = sizeof($this->buffer);
//        for ($i = 0; $i < $max; $i++) {
//            $str = utf8_encode($this->buffer[$i]);
//            \Log::debug($str);
//
//            // fill a temporary buffer with the message
//            $response_buffer[] = $str;
//        }
//
//        // re-initialize the original buffer
//        $this->buffer = array();
//
////        \Log::debug('$response_buffer : ' . print_r($response_buffer, true));
//        // ---------------------------------------------------
//        // - response
//        // ---------------------------------------------------
//        return response()->json(
//                        [
//                    'data' => $response_buffer, // sending back the logs
//                    'message' => $msg
//                        ], $this->HTTP_OK);
//    }
}
