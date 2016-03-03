<?php

namespace App\Http\Controllers;

use Config;
use App\User;
use App\Employee;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;

class EmployeesController extends Controller {

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
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index() {

        // ---------------------------------------------------
        // - lists of all the employees
        // ---------------------------------------------------
        $employees = EmployeesController:: getAllEmployees();
        return response()->json(
                        [
                    'data' => $employees,
                    'message' => 'Succesfull request'
                        ], $this->HTTP_OK);
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create() {
        //
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request) {

        // ---------------------------------------------------
        // - create and store a new employee
        // ---------------------------------------------------
        $employee = Employee::create($request->all());

        // ---------------------------------------------------
        // - create and store a new user
        // ---------------------------------------------------
        $role_id = 0;
        if ($request->isProjectManager === 1) {
            $role_id = 2;
        } else if ($request->isTeamLeader === 1) {
            $role_id = 3;
        } else {
            $role_id = 4;
        }

        $auth = [
            'employeeId' => $employee->id,
            'username' => $employee->employeeId,
            'role_id' => $role_id
        ];

        $user = User::create($auth);

        // ---------------------------------------------------
        // - response
        // ---------------------------------------------------
        return response()->json([
                    'message' => 'Employee created succesfully',
                    'data' => $employee
                        ], 200);
    }

    /**
     * Retrieve all information about an employee
     *
     * @param  int  $id the employee id
     * @return \Illuminate\Http\Response
     */
    public function show($id) {

        $employeeData = $this->getEmployeeData($id);

        // ---------------------------------------------------
        // - the response
        // ---------------------------------------------------

        return response()->json(
                        [
                    'data' => $employeeData,
                    'message' => 'Succesfull request'
                        ], $this->HTTP_OK);
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id) {
        
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id) {

        // ---------------------------------------------------
        // -update the employee 
        // ---------------------------------------------------
        $employee = Employee::find($id);

        $employee->firstName = trim($request->firstName) !== '' ? $request->firstName : null;
        $employee->lastName = trim($request->lastName) !== '' ? $request->lastName : null;
        $employee->fullName = trim($request->fullName) !== '' ? $request->fullName : null;
        $employee->avatar = trim($request->avatar) !== '' ? $request->avatar : null;
        $employee->location = trim($request->location) !== '' ? $request->location : null;
        $employee->phone = trim($request->phone) !== '' ? $request->phone : null;
        $employee->email = trim($request->email) !== '' ? $request->email : null;
        $employee->productivity = trim($request->productivity) !== '' ? $request->productivity : null;
        $employee->isProjectManager = trim($request->isProjectManager) !== '' ? $request->isProjectManager : null;
        $employee->isTeamLeader = trim($request->isTeamLeader) !== '' ? $request->isTeamLeader : null;
        $employee->employementType = trim($request->employementType) !== '' ? $request->employementType : null;

        $employee->save();

        return response()->json([
                    'message' => 'Skill updated succesfully',
                    'data' => $employee
                        ], 200);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id) {
        Employee::destroy($id);
    }

    /**
     * 
     * @param type $id
     * @return type
     */
    public static function getEmployeeData($id) {

        $employee = EmployeesController::getEmployeePersonalData($id);
        $team = TeamsController::getEmployeeTeamData($id);
        $recentTasks = EmployeesTasksController::getEmployeeRecentTasksData($id);
        $holidays = HolidaysController::getEmployeeHolidaysData($id);
        $tasks = EmployeesTasksController::getEmployeeTasksData($id);
        $skills = SkillsController::getEmployeeSkillsData($id);
        $nonWorkingDays = NonWorkingDaysController:: getNonWorkingDays();

        return array($employee, $team, $recentTasks, $holidays, $tasks, $skills, $nonWorkingDays);
    }

    /**
     * 
     * @param type $id
     * @return type
     */
    public static function getEmployeePersonalData($id) {

        // ---------------------------------------------------
        // - get the employee record
        // ---------------------------------------------------
        $employees = \DB::table('employees')
                ->where('employees.id', '=', $id)
                ->get();

        if (empty($employees)) {
            $employees = array();
        }

        return $employees;
    }

    /**
     * 
     */
    public static function getAllEmployees() {

        $employees = Employee::all();

        if (empty($employees)) {
            $employees = array();
        }

        return $employees;
    }

}
