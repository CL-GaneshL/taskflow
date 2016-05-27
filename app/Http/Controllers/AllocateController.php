<?php

namespace App\Http\Controllers;

use Config;
use App\Helpers\SettingsHelper;
use App\Helpers\MessagesHelper;
use App\Http\Controllers\Controller;
use Symfony\Component\Process\Process;

class AllocateController extends Controller {

    private $HTTP_OK = null;
    private $HTTP_INTERNAL_SERVER_ERROR = null;
    private $HTTP_INTERNAL_SERVER_ERROR_MSG = null;

    public function __construct() {

        $this->HTTP_OK = Config::get('caratlane.dbconstants.HTTP_OK');
        $this->HTTP_INTERNAL_SERVER_ERROR = Config::get('caratlane.dbconstants.HTTP_INTERNAL_SERVER_ERROR');
        $this->HTTP_INTERNAL_SERVER_ERROR_MSG = Config::get('caratlane.dbconstants.HTTP_INTERNAL_SERVER_ERROR_MSG');

        // -------------------------------------
        // Apply the jwt.auth middleware to all methods in this controller
        // -------------------------------------
        $this->middleware('jwt.auth');
    }

    /**
     * 
     */
    public function allocate() {

        $settings = new SettingsHelper();

        \Log::debug('Task allocation : ...........................................');

        $action = 'generate';
        $java_bin = $settings->getJavaBinary();
        $jar = $settings->getJarFile();
        $commandline = $settings->getCommandLine($action);

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
    public function test() {

        $settings = new SettingsHelper();
        $messages = new MessagesHelper();

        $action = 'check';
        $java_bin = $settings->getJavaBinary();
        $jar = $settings->getJarFile();
        $commandline = $settings->getCommandLine($action);

        $command = $java_bin . ' -jar ' . $jar . ' ' . $commandline;
        \Log::debug('   - Command : [' . $command . ']');

        $process = new Process($command);
        $process->start();

        $warning_count = 0;
        $error_count = 0;

        $process->wait(function ($type, $buffer)
                use (&$messages, &$warning_count, &$error_count) {

            $bufferlogList = preg_split("/\\r\\n|\\r|\\n/", $buffer);

            foreach ($bufferlogList as $bufferLog) {

                $is_test_log = strpos($bufferLog, "TEST_MGS ") === 0;

                if ($is_test_log === true) {

                    // remove unwanted prefix
                    $clean_buffer = utf8_encode($bufferLog);
                    $log = str_replace("TEST_MGS ", "", $clean_buffer);

                    // \Log::debug('[' . $bufferLog . ']');

                    $warning_count += (strpos($log, "WARNING") === 0) ? 1 : 0;
                    $error_count += (strpos($log, "SEVERE") === 0 ) ? 1 : 0;

                    \Log::debug('$warning_count = ' . $warning_count);
                    \Log::debug('$error_count = ' . $error_count);

                    // will be sent to the front end
                    $messages->log($log);
                }
            }
        });        

        $messages->log('RESULTS_WARNINGS : ' . $warning_count);
        $messages->log('RESULTS_ERRORS : ' . $error_count);

        return $messages->publish("TaskGenerator test.");
    }

    /**
     * 
     */
    public function reset() {

        $settings = new SettingsHelper();

        \Log::debug('Task reset : ...........................................');

        $action = 'reset';
        $java_bin = $settings->getJavaBinary();
        $jar = $settings->getJarFile();
        $commandline = $settings->getCommandLine($action);

        $command = $java_bin . ' -jar ' . $jar . ' ' . $commandline;
        \Log::debug('   - Command : [' . $command . ']');

        $process = new Process($command);
        $process->start();

        $process->wait(function ($type, $buffer) {
            \Log::debug($buffer);
        });
    }

}
