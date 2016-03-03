<?php

namespace App\Http\Controllers;

use Config;
use App\Team;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;

class TeamsEmployeesController extends Controller {

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
    public function index(Request $request, $id) {

        // ---------------------------------------------------
        // - list of all members of this team
        // ---------------------------------------------------
        $members = \DB::table('employees')
                ->select('employees.*')
                ->join('teams_have_employees', 'teams_have_employees.employeeId', '=', 'employees.id')
                ->where('teams_have_employees.teamId', '=', $id)
                ->get();

        if (empty($members)) {
            $members = array();
        }

        return response()->json(
                        [
                    'data' => $members,
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
        $teamId = $request->teamId;
        $employeeId = $request->employeeId;

        if ($action === 'delete') {

            \DB::table('teams_have_employees')
                    ->where('employeeId', '=', $employeeId)
                    ->where('teamId', '=', $teamId)
                    ->delete();
        } else if ($action === 'add') {

            \DB::table('teams_have_employees')
                    ->insert(
                            array(
                                'employeeId' => $employeeId,
                                'teamId' => $teamId
                            )
            );
        }

        return response()->json([
                    'message' => 'Team updated succesfully'
                        ], 200);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id) {
        //
    }

}
