<?php

use Illuminate\Database\Seeder;

class ProjectsTableSeeder extends Seeder {

    public function run() {

        // right now
        $now = mktime(0, 0, 0);

        // ---------------------------------------------
        // project 1
        // ---------------------------------------------
        // project started 5 days ago
        $day = $now + ( -5 * 24 * 60 * 60);
        $start = date('Y-m-d H:i:s', $day);

        $templateId = 1;
        $template = \DB::table('project_templates')->where('id', $templateId)->get();
        $template_reference = $template[0]->reference;

        $project_reference = str_replace(' ', '_', $template_reference)
                . '_' . date("M", $day) . '_' . date("Y", $day) . '_1';

        DB::table('projects')->insert([
            [
                'reference' => $project_reference,
                'template_id' => $templateId,
                'nb_products' => 15,
                'priority' => "8",
                'start_date' => date('Y-m-d', $day)
            ]
        ]);
        // ---------------------------------------------
        // project 2
        // ---------------------------------------------
        // project started 4 days ago
        $day = $now + ( -4 * 24 * 60 * 60);
        $start = date('Y-m-d H:i:s', $day);

        $templateId = 1;
        $template = \DB::table('project_templates')->where('id', $templateId)->get();
        $template_reference = $template[0]->reference;

        $project_reference = str_replace(' ', '_', $template_reference)
                . '_' . date("M", $day) . '_' . date("Y", $day) . '_2';

        DB::table('projects')->insert([
            [
                'reference' => $project_reference,
                'template_id' => $templateId,
                'nb_products' => 10,
                'priority' => "4",
                'start_date' => date('Y-m-d', $day)
            ]
        ]);
        // ---------------------------------------------
        // project 3
        // ---------------------------------------------
        // project started 10 days ago
        $day = $now + ( -10 * 24 * 60 * 60);
        $start = date('Y-m-d H:i:s', $day);

        $templateId = 4;
        $template = \DB::table('project_templates')->where('id', $templateId)->get();
        $template_reference = $template[0]->reference;

        $project_reference = str_replace(' ', '_', $template_reference)
                . '_' . date("M", $day) . '_' . date("Y", $day);

        DB::table('projects')->insert([
            [
                'reference' => $project_reference,
                'template_id' => $templateId,
                'nb_products' => 20,
                'priority' => "8",
                'start_date' => date('Y-m-d', $day)
            ]
        ]);
        // ---------------------------------------------
        // project 4
        // ---------------------------------------------
        // project started 5 days ago
        $day = $now + ( -5 * 24 * 60 * 60);
        $start = date('Y-m-d H:i:s', $day);

        $templateId = 6;
        $template = \DB::table('project_templates')->where('id', $templateId)->get();
        $template_reference = $template[0]->reference;

        $project_reference = str_replace(' ', '_', $template_reference)
                . '_' . date("M", $day) . '_' . date("Y", $day);

        DB::table('projects')->insert([
            [
                'reference' => $project_reference,
                'template_id' => $templateId,
                'nb_products' => 15,
                'priority' => "8",
                'start_date' => date('Y-m-d', $day)
            ]
        ]);
        // ---------------------------------------------
        // project 5
        // ---------------------------------------------
        // project started 15 days ago
        $day = $now + ( -15 * 24 * 60 * 60);
        $start = date('Y-m-d H:i:s', $day);

        $templateId = 9;
        $template = \DB::table('project_templates')->where('id', $templateId)->get();
        $template_reference = $template[0]->reference;

        $project_reference = str_replace(' ', '_', $template_reference)
                . '_' . date("M", $day) . '_' . date("Y", $day);

        DB::table('projects')->insert([
            [
                'reference' => $project_reference,
                'template_id' => $templateId,
                'nb_products' => 15,
                'priority' => "5",
                'start_date' => date('Y-m-d', $day),
                'open' => false,
            ]
        ]);
    }

}
