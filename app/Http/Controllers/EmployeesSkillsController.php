<?php

namespace App\Http\Controllers;

use Config;
use App\Employee;
use App\EmployeesHaveSkills;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;

class EmployeesSkillsController extends Controller {

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
    public function index($id) {

        // ---------------------------------------------------
        // - list of all skills of the employee $id
        // ---------------------------------------------------
        $skills = \DB::table('skills')
                ->select('skills.id', 'skills.reference', 'skills.designation')
                ->join('employees_have_skills', 'employees_have_skills.skillId', '=', 'skills.id')
                ->where('employees_have_skills.employeeId', '=', $id)
                ->orderBy('skills.id', 'asc')
                ->get();

        if (empty($skills)) {
            $skills = array();
        }

        return response()->json(
                        [
                    'data' => $skills,
                    'message' => 'Succesfull request'
                        ], $this->HTTP_OK);
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create() {
        
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request) {
        //
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id) {
        
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id) {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id) {

        $action = $request->action;
        $employeeId = $id;
        $skillId = $request->skillId;

        \Log::debug('$action = ' . $action);
        \Log::debug('$employeeId = ' . $employeeId);
        \Log::debug('$skillId = ' . $skillId);

        if ($action === 'delete') {

            \DB::table('employees_have_skills')
                    ->where('employeeId', '=', $employeeId)
                    ->where('skillId', '=', $skillId)
                    ->delete();
        } else if ($action === 'add') {

            \DB::table('employees_have_skills')
                    ->insert(
                            array(
                                'employeeId' => $employeeId,
                                'skillId' => $skillId
                            )
            );
        }

        return response()->json([
                    'message' => 'Employee updated succesfully',
                        ], 200);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id) {
        
    }

}
