<?php

use Illuminate\Database\Seeder;

class ProjectTemplatesHaveSkillsTableSeeder extends Seeder {

    public function run() {

        DB::table('project_templates_have_skills')->insert([
            // ------------------------------------------
            // - project Jadau
            // ------------------------------------------
            [
                'template_id' => "1",
                'skill_id' => "3" // 3D Model - Complex           
            ],
            [
                'template_id' => "1",
                'skill_id' => "5" // Render, Clean-up & Re-size  - Complex
            ],
            // ------------------------------------------
            // - project LA Ruche - 3D Theme - rnd
            // ------------------------------------------
            [
                'template_id' => "2",
                'skill_id' => "2" // 3D Model - Medium
            ],
            [
                'template_id' => "2",
                'skill_id' => "4" // Render, Clean-up & Re-size - Simple & Medium
            ],
            // ------------------------------------------
            // - Gemstone & Diamond Collection Suidhaaga
            // ------------------------------------------
            [
                'template_id' => "3",
                'skill_id' => "3" // 3D Model - Complex
            ],
            [
                'template_id' => "3",
                'skill_id' => "4" // Render, Clean-up & Re-size - Simple & Medium
            ],
            // ------------------------------------------
            // - Mens Cufflinka with Overall Mens Category
            // ------------------------------------------
            [
                'template_id' => "4",
                'skill_id' => "3" // 3D Model - Complex
            ],
            [
                'template_id' => "4",
                'skill_id' => "4" // Render, Clean-up & Re-size - Simple & Medium
            ],
            // ------------------------------------------
            // - Solitaire Single line bangles
            // ------------------------------------------
            [
                'template_id' => "5",
                'skill_id' => "2" // 3D Model - Medium
            ],
            [
                'template_id' => "5",
                'skill_id' => "4" // Render, Clean-up & Re-size - Simple & Medium
            ],
            // ------------------------------------------
            // - Anniversary Bands (6)
            // ------------------------------------------
            [
                'template_id' => "6",
                'skill_id' => "2" // 3D Model - Medium
            ],
            [
                'template_id' => "6",
                'skill_id' => "4" // Render, Clean-up & Re-size - Simple & Medium
            ],
            // ------------------------------------------
            // - FK - Evil Eye
            // ------------------------------------------
            [
                'template_id' => "7",
                'skill_id' => "3" // 3D Model - Complex
            ],
            [
                'template_id' => "7",
                'skill_id' => "4" // Render, Clean-up & Re-size - Simple & Medium
            ],
            // ------------------------------------------
            // - Diamond Collection Suidhaaga
            // ------------------------------------------
            [
                'template_id' => "8",
                'skill_id' => "3" // 3D Model - Complex
            ],
            [
                'template_id' => "8",
                'skill_id' => "4" // Render, Clean-up & Re-size - Simple & Medium
            ],
            // ------------------------------------------
            // - CNC - Chains
            // ------------------------------------------
            [
                'template_id' => "9",
                'skill_id' => "3" // 3D Model - Complex
            ],
            [
                'template_id' => "9",
                'skill_id' => "4" // Render, Clean-up & Re-size - Simple & Medium
            ],
            // ------------------------------------------
            // - Buccellati
            // ------------------------------------------
            [
                'template_id' => "10",
                'skill_id' => "3" // 3D Model - Complex
            ],
            [
                'template_id' => "10",
                'skill_id' => "4" // Render, Clean-up & Re-size - Simple & Medium
            ],
            // ------------------------------------------
            // - Band of Grace (11)
            // ------------------------------------------
            [
                'template_id' => "11",
                'skill_id' => "2" // 3D Model - Medium
            ],
            [
                'template_id' => "11",
                'skill_id' => "4" // Render, Clean-up & Re-size - Simple & Medium
            ],
            // ------------------------------------------
            // - Ind selection( 60)
            // ------------------------------------------
            [
                'template_id' => "11",
                'skill_id' => "3" // 3D Model - Complex
            ],
            // ------------------------------------------
            // - Marketing team work - Videos & Renders
            // ------------------------------------------
            [
                'template_id' => "12",
                'skill_id' => null
            ],
            // ------------------------------------------
            // - Comparison Videos
            // ------------------------------------------
            [
                'template_id' => "13",
                'skill_id' => null
            ],
            // ------------------------------------------
            // - VTO Renders
            // ------------------------------------------
            [
                'template_id' => "14",
                'skill_id' => "13" // VTO Renders
            ],
            // ------------------------------------------
            // - Yellow to white gold
            // ------------------------------------------
            [
                'template_id' => "15",
                'skill_id' => "11" // Yellow gold to white gold - CAD Edit/Setup & Cleanup
            ]
                // ------------------------------------------
        ]);
    }

}
