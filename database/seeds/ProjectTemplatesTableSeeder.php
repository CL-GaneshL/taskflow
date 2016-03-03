<?php

use Illuminate\Database\Seeder;

class ProjectTemplatesTableSeeder extends Seeder {

    public function run() {
        DB::table('project_templates')->insert([
            [
                'reference' => "JADAU",
                'designation' => "Jadau"
            ],
            [
                'reference' => "RUCHE3D",
                'designation' => "LA Ruche - 3D Theme - rnd"
            ],
            [
                'reference' => "SUIDHAAGA",
                'designation' => "Gemstone & Diamond Collection Suidhaaga"
            ],
            [
                'reference' => "CUFFLINKA",
                'designation' => "Mens Cufflinka with Overall Mens Category"
            ],
            [
                'reference' => "SOLITAIRE",
                'designation' => "Solitaire Single line bangles"
            ],
            [
                'reference' => "BANDS6",
                'designation' => "Anniversary Bands (6)"
            ],
            [
                'reference' => "EVILEYE",
                'designation' => "FK - Evil Eye"
            ],
            [
                'reference' => "DSUIDHAAGA",
                'designation' => "Diamond Collection Suidhaaga"
            ],
            [
                'reference' => "CNC",
                'designation' => "CNC - Chains"
            ],
            [
                'reference' => "BUCCELLATI",
                'designation' => "Buccellati"
            ],
            [
                'reference' => "GRACE11",
                'designation' => "Band of Grace (11)"
            ],
            [
                'reference' => "IND60",
                'designation' => "Ind selection (60)"
            ],
            [
                'reference' => "MARKETING",
                'designation' => "Marketing team work - Videos & Renders"
            ],
            [
                'reference' => "VIDEOS",
                'designation' => "Comparison Videos"
            ],
            [
                'reference' => "VTORENDERS",
                'designation' => "VTO Renders"
            ],
            [
                'reference' => "Y2WHITE",
                'designation' => "Yellow to white gold"
            ]
        ]);
    }

}
