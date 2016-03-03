<?php

namespace App\Http\Controllers;

use Config;
use App\Skill;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;

class SkillsController extends Controller {

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
        // - retrieve all skills
        // ---------------------------------------------------
        $skills = SkillsController:: getAllOpenSkills();

        // ---------------------------------------------------
        // - response
        // ---------------------------------------------------
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
        // - valid received data
        // ---------------------------------------------------
        $this->validate($request, [
            'reference' => 'unique:skills|required|max:7',
            'designation' => 'max:35',
        ]);

        // ---------------------------------------------------
        // - create and store the data
        // ---------------------------------------------------
        $skill = Skill::create($request->all());

        // ---------------------------------------------------
        // - response
        // ---------------------------------------------------
        return response()->json([
                    'message' => 'Skill created succesfully',
                    'data' => $skill
                        ], 200);
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
        
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id) {

        \DB::table('skills')
                ->where('skills.id', '=', $id)
                ->update(array('open' => 0));
    }

    /**
     * 
     * @param type $skills
     * @return type
     */
    private function transformSkillsCollection($skills) {
        return array_map([$this, 'transformSkill'], $skills->toArray());
    }

    /**
     * 
     * @param type $skill
     * @return type
     */
    private function transformSkill($skill) {
        return [
            'reference' => $skill['reference'],
            'designation' => $skill['designation']
        ];
    }

    /**
     * 
     * @return type
     */
    public static function getAllOpenSkills() {

        $skills = \DB::table('skills')
                ->where('open', '=', 1)
                ->orderBy('id', 'asc')
                ->get();

        if (empty($skills)) {
            $skills = array();
        }

        return $skills;
    }

    /**
     * 
     * @param type $id
     * @return type
     */
    public static function getEmployeeSkillsData($id) {

        // ---------------------------------------------------
        // - list of all skills of employee $id
        // ---------------------------------------------------
        $skills = \DB::table('skills')
                ->select('skills.id', 'skills.reference', 'skills.designation', 'skills.duration')
                ->join('employees_have_skills', 'employees_have_skills.skillId', '=', 'skills.id')
                ->where('employees_have_skills.employeeId', '=', $id)
                ->orderBy('skills.id', 'asc')
                ->get();

        if (empty($skills)) {
            $skills = array();
        }

        return $skills;
    }

}
