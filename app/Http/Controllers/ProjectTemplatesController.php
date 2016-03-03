<?php

namespace App\Http\Controllers;

use Config;
use App\Template;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;

class ProjectTemplatesController extends Controller {

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
        // - list of all project templates
        // ---------------------------------------------------
        $templates = ProjectTemplatesController::getAllTemplates();

        // ---------------------------------------------------
        // - retrieve all skills
        // ---------------------------------------------------
        $skills = SkillsController:: getAllOpenSkills();

        // ---------------------------------------------------
        // - response
        // ---------------------------------------------------
        return response()->json(
                        [
                    'data' => array($templates, $skills),
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
        $template = Template::create($request->all());

        // ---------------------------------------------------
        // - response
        // ---------------------------------------------------
        return response()->json([
                    'data' => $template,
                    'message' => 'Template created succesfully'
                        ], 200);
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id) {

        // ---------------------------------------------------
        // - retrieve all skills for that Temaplate
        // ---------------------------------------------------
        $skills = \DB::table('skills')
                ->select('skills.*')
                ->join('project_templates_have_skills', 'project_templates_have_skills.skill_id', '=', 'skills.id')
                ->where('project_templates_have_skills.template_id', '=', $id)
                ->orderBy('skills.id', 'asc')
                ->get();

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
        $template_id = $request->template_id;
        $skill_id = $request->skill_id;

        \Log::debug('$action = ' . $action);
        \Log::debug('$template_id = ' . $template_id);
        \Log::debug('$skill_id = ' . $skill_id);

        if ($action === 'delete') {

            \DB::table('project_templates_have_skills')
                    ->where('template_id', '=', $template_id)
                    ->where('skill_id', '=', $skill_id)
                    ->delete();
        } else if ($action === 'add') {

            \DB::table('project_templates_have_skills')
                    ->insert(
                            array(
                                'template_id' => $template_id,
                                'skill_id' => $skill_id
                            )
            );
        }

        return response()->json([
                    'message' => 'Team updated succesfully'
                        ], 200);
    }

    /**
     * Close a Project Template.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($template_id) {

        \DB::table('project_templates')
                ->where('project_templates.id', '=', $template_id)
                ->update(array('open' => 0));

        $template = Template::find($template_id);

        return response()->json([
                    'message' => 'Template closed succesfully',
                    'data' => $template
                        ], 200);
    }

    /**
     * 
     */
    public static function getAllTemplates() {

        return \DB::table('project_templates')
                        ->where('open', '=', 1)
                        ->orderBy('id', 'asc')
                        ->get();
    }

}
