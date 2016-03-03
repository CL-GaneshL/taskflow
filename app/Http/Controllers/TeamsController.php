<?php

namespace App\Http\Controllers;

use Config;
use App\Team;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;

class TeamsController extends Controller {

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
        // - list of all teams
        // ---------------------------------------------------

        $teams = \DB::table('teams')
                ->leftjoin('employees', 'teams.teamLeaderId', '=', 'employees.id')
                ->select(
                        'teams.id', // team ID
                        'teams.teamName', // team name
                        'employees.fullName as teamLeaderFullName'      // the fullname of the team leader
                )
                ->get();

        // ---------------------------------------------------
        // - list of all employees team leaders
        // ---------------------------------------------------

        $teamLeaders = \DB::table('employees')
                ->select('employees.id', 'employees.fullName')
                ->where('employees.isTeamLeader', '=', 1)
                ->get();

        // ---------------------------------------------------
        // - list of all employees ( excluding Project Managers )
        // ---------------------------------------------------
        $allEmployees = \DB::table('employees')
                ->where('employees.isProjectManager', '<>', '1')
                ->get();

        // ---------------------------------------------------
        // - response
        // ---------------------------------------------------
        return response()->json(
                        [
                    'data' => array($teams, $teamLeaders, $allEmployees),
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
        // - create and store the team
        // ---------------------------------------------------
        $newTeam = Team::create($request->all());

        // ---------------------------------------------------
        // - return the team record including the team lead 
        // - full name if it exists.
        // ---------------------------------------------------
        $newTeamId = $newTeam->id;

        $team = \DB::table('teams')
                ->leftjoin('employees', 'teams.teamLeaderId', '=', 'employees.id')
                ->select(
                        'teams.id', // team ID
                        'teams.teamName', // team name
                        'employees.fullName as teamLeaderFullName'      // the fullname of the team leader
                )
                ->where('teams.id', '=', $newTeamId)
                ->get();

        // ---------------------------------------------------
        // - response
        // ---------------------------------------------------
        return response()->json([
                    'message' => 'Team created succesfully',
                    'data' => $team
                        ], 200);
    }

    /**
     * retrieve the team with teamId = $id
     *  - team id
     *  - team name
     *  - team leader fullname
     *  - the list of team members
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id) {

        // ---------------------------------------------------
        //   retrieve the list of team members
        // ---------------------------------------------------
        $teams = \DB::table('teams')
                ->join('employees', 'teams.teamLeaderId', '=', 'employees.id')
                ->select(
                        'teams.id', // team ID
                        'teams.teamName', // team name
                        'employees.fullName as teamLeaderFullName'      // the fullname of the team leader
                )
                ->get();

        if (empty($teams)) {
            $teams = array();
        }

        // ---------------------------------------------------
        // - send back data
        // ---------------------------------------------------

        $teamLeaders = \DB::table('employees')
                ->select('employees.id', 'employees.fullName')
                ->where('employees.isTeamLeader', '=', 1)
                ->get();

        if (empty($teamLeaders)) {
            $teamLeaders = array();
        }

        return response()->json(
                        [
                    'data' => array($teams, $teamLeaders),
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
//
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id) {

        // detach all employees that are part of this team
        \DB::table('teams_have_employees')
                ->where('teamId', '=', $id)
                ->delete();

        Team::destroy($id);
    }

    /**
     * 
     * @param type $id
     * @return type
     */
    public static function getEmployeeTeamData($id) {

        // ---------------------------------------------------
        // - get the employee's team
        // ---------------------------------------------------
        return \DB::table('teams')
                        ->select('teams.id', 'teams.teamName')
                        ->join('teams_have_employees', 'teams_have_employees.teamId', '=', 'teams.id')
                        ->where('teams_have_employees.employeeid', '=', $id)
                        ->get();
    }

}
