<?php

namespace App\Http\Controllers;

use Config;
use App\Http\Controllers\Controller;
use Symfony\Component\Process\Process;

class AllocateController extends Controller {

    private $HTTP_OK = null;
    private $HTTP_INTERNAL_SERVER_ERROR = null;
    private $HTTP_INTERNAL_SERVER_ERROR_MSG = null;
    private $buffer = null;

    public function __construct() {

        $this->HTTP_OK = Config::get('caratlane.dbconstants.HTTP_OK');
        $this->HTTP_INTERNAL_SERVER_ERROR = Config::get('caratlane.dbconstants.HTTP_INTERNAL_SERVER_ERROR');
        $this->HTTP_INTERNAL_SERVER_ERROR_MSG = Config::get('caratlane.dbconstants.HTTP_INTERNAL_SERVER_ERROR_MSG');

        // -------------------------------------
        // Apply the jwt.auth middleware to all methods in this controller
        // -------------------------------------
        $this->middleware('jwt.auth');

        $this->buffer = array();
    }

    /**
     * 
     */
    public function allocate() {

        \Log::debug('Task allocation : ...........................................');

        $action = 'generate';
        $java_bin = $this->getJavaBinary();
        $jar = $this->getJarFile();
        $commandline = $this->getCommandLine($action);

        $command = $java_bin . ' -jar ' . $jar . ' ' . $commandline;
        \Log::debug('   - Command : [' . $command . ']');

        $process = new Process($command);
        $process->start();

        $process->wait(function ($type, $buffer) {
            \Log::debug($buffer);
        });
    }

    /**
     * 
     */
    public function reset() {

        \Log::debug('Task reset : ...........................................');

        $action = 'reset';
        $java_bin = $this->getJavaBinary();
        $jar = $this->getJarFile();
        $commandline = $this->getCommandLine($action);

        $command = $java_bin . ' -jar ' . $jar . ' ' . $commandline;
        \Log::debug('   - Command : [' . $command . ']');

        $process = new Process($command);
        $process->start();

        $process->wait(function ($type, $buffer) {
            \Log::debug($buffer);
        });
    }

    private function getDebugMode() {

        $mode = Config::get('app.debug');
        if ($mode === true) {
            $mode = 'debug';
        } else {
            $mode = '';
        }

        return $mode;
    }

    private function getJavaBinary() {

        $java_bin_dir = Config::get('java.JAVA_HOME') . DIRECTORY_SEPARATOR . 'bin';
        $java_bin = '"' . $java_bin_dir . DIRECTORY_SEPARATOR . 'java' . '"';

        return $java_bin;
    }

    private function getJarFile() {

        $jar_path = base_path('resources/bin');
        $jar_file = Config::get('caratlane.constants.TASK_GENERATOR_JAR');
        $jar = '"' . $jar_path . DIRECTORY_SEPARATOR . $jar_file . '"';

        return $jar;
    }

    private function getLogPath() {

        $log_path = storage_path('logs');
        $log_file = Config::get('caratlane.constants.TASK_GENERATOR_LOG_FILE');
        $logpath = '"' . $log_path . DIRECTORY_SEPARATOR . $log_file . '"';

        return $logpath;
    }

    private function getDbHost() {

        $default = Config::get('database.default');
        $config = Config::get('database.connections.' . $default);

        return $config['host'];
    }

    private function getDbPort() {

        $default = Config::get('database.default');
        $config = Config::get('database.connections.' . $default);

        return $config['port'];
    }

    private function getDatabase() {

        $default = Config::get('database.default');
        $config = Config::get('database.connections.' . $default);

        return $config['database'];
    }

    private function getDbUsername() {

        $default = Config::get('database.default');
        $config = Config::get('database.connections.' . $default);

        return $config['username'];
    }

    private function getDbPassword() {

        $default = Config::get('database.default');
        $config = Config::get('database.connections.' . $default);

        return $config['password'];
    }

    private function getCommandLine($action) {

        $commandline = '';
        $commandline = $commandline . ' -host=' . $this->getDbHost();
        $commandline = $commandline . ' -port=' . $this->getDbPort();
        $commandline = $commandline . ' -database=' . $this->getDatabase();
        $commandline = $commandline . ' -username=' . $this->getDbUsername();
        $commandline = $commandline . ' -password=' . $this->getDbPassword();
        $commandline = $commandline . ' -logpath=' . $this->getLogPath();
        $commandline = $commandline . ' -mode=' . $this->getDebugMode();
        $commandline = $commandline . ' -action=' . $action;

        return $commandline;
    }

    /**
     * 
     */
    public function getTaskflowConfiguration() {

        $this->log('');
        $this->log('............................');
        $this->log('TaskGenerator configuration :');

        $this->log('   - debug mode    : [' . $this->getDebugMode() . ']');
        $this->log('   - java binary   : [' . $this->getJavaBinary() . ']');
        $this->log('   - jar file      : [' . $this->getJarFile() . ']');
        $this->log('   - log path      : [' . $this->getLogPath() . ']');
        $this->log('   - db host       : [' . $this->getDbHost() . ']');
        $this->log('   - db port       : [' . $this->getDbPort() . ']');
        $this->log('   - database      : [' . $this->getDatabase() . ']');
        $this->log('   - db username   : [' . $this->getDbUsername() . ']');
        $this->log('   - db password   : [' . $this->getDbPassword() . ']');
        $this->log('   - command line  : [' . $this->getCommandLine("") . ']');

        $action = 'version';
        $java_bin = $this->getJavaBinary();
        $jar = $this->getJarFile();
        $commandline = $this->getCommandLine($action);
        $command = $java_bin . ' -jar ' . $jar . ' ' . $commandline;

        $process = new Process($command);
        $process->start();

        $this->log("TaskGenerator version : ");
        $process->wait(function ($type, $buffer) {
            $str = str_replace("\r\n", "", $buffer);
            if (strlen($str) != 0) {
                $this->log($str);
            }
        });

        $this->log('--');

        return $this->publish("TaskGenerator configuration.");
    }

    /**
     * 
     */
    public function getJavaConfiguration() {

        $this->log('');
        $this->log('............................');
        $this->log('TaskGenerator Java version :');

        $process = new Process($this->getJavaBinary() . ' ' . '-version');
        $process->start();
        $process->wait(function ($type, $buffer) {

            $str = str_replace("\r\n", "", $buffer);
            if (strlen($str) != 0) {
                $this->log($str);
            }
        });

        $this->log('--');

        return $this->publish("TaskGenerator Java version.");
    }

    /**
     * 
     * @param type $str
     */
    private function log($str) {
        $this->buffer [] = $str;
    }

    /**
     * 
     * @return type
     */
    private function publish($msg) {

        // create a temporary buffer
        // this buffer will be return the the client
        // while the original buffer is destroy
        $response_buffer = array();

        // trace the msg in the log file
        $max = sizeof($this->buffer);
        for ($i = 0; $i < $max; $i++) {
            $str = utf8_encode($this->buffer[$i]);
            \Log::debug($str);

            // fill a temporary buffer with the message
            $response_buffer[] = $str;
        }

        // re-initialize the original buffer
        $this->buffer = array();

//        \Log::debug('$response_buffer : ' . print_r($response_buffer, true));
        // ---------------------------------------------------
        // - response
        // ---------------------------------------------------
        return response()->json(
                        [
                    'data' => $response_buffer, // sending back the logs
                    'message' => $msg
                        ], $this->HTTP_OK);
    }

}
