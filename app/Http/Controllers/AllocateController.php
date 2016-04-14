<?php

namespace App\Helpers\Controllers;

use Config;
use App\Http\Controllers\Controller;
use App\Http\Controllers\SettingsHelper;
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
