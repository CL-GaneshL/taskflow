<?php

namespace App\Http\Controllers;

use Config;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;

class ProjectsTasksController extends Controller {

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
    public function index() {

        $tasks = ProjectsTasksController::getProjectsTasksData();
        $holidays = HolidaysController::getHolidaysData();
        $nonWorkingDays = NonWorkingDaysController:: getNonWorkingDays();
        $employees = EmployeesController:: getAllEmployees();
        $projects = ProjectsController:: getAllOpenProjects();

        // ---------------------------------------------------
        // \Log::debug('index : $tasks : ' . print_r(sizeof($tasks), true));
        // \Log::debug('index : $holidays : ' . print_r(sizeof($holidays), true));
        // \Log::debug('index : $nonWorkingDays : ' . print_r(sizeof($nonWorkingDays), true));
        // \Log::debug('index : $employees : ' . print_r($employees, true));
        // \Log::debug('index : $projects : ' . print_r(sizeof($projects), true));
        // ---------------------------------------------------
        // ---------------------------------------------------
        // - response
        // ---------------------------------------------------
        return response()->json(
                        [
                    'data' => array($tasks, $holidays, $nonWorkingDays, $employees, $projects),
                    'message' => 'Succesfull request'
                        ], $this->HTTP_OK);
    }

    /**
     * 
     * @param type $id
     * @return type
     */
    public static function getProjectsTasksData() {

        // ---------------------------------------------------
        // - list of all tasks for that employee $id
        // ---------------------------------------------------
        $tasks = \DB::table('v_tasks_info')
                ->select('v_tasks_info.*')
                ->where('v_tasks_info.open', '=', 1)
                ->orderBy('v_tasks_info.id', 'asc')
                ->get();

        if (empty($tasks)) {
            $tasks = array();
        }

        // ---------------------------------------------------
        // \Log::debug('getEmployeeTasksData : nb tasks : ' . print_r(sizeof($tasks), true));
        // ---------------------------------------------------

        return $tasks;
    }

}
