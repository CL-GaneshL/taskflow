<?php

use Illuminate\Database\Seeder;

class TeamsTableSeeder extends Seeder {

    public function run() {
        DB::table('teams')->insert([
            [
                'teamName' => "Imaging - Clean Up",
                'teamLeaderId' => "2"   // CL0138	Appolin A
            ],
            [
                'teamName' => "Imaging - Marketing video",
                'teamLeaderId' => "3"   // CL0014	Prasanna T
            ],
            [
                'teamName' => "Imaging - 3d model",
                'teamLeaderId' => null
            ],
            [
                'teamName' => "Imaging - VTO",
                'teamLeaderId' => null
            ],
            [
                'teamName' => "Imaging - Photoshoot",
                'teamLeaderId' => null
            ]
        ]);
    }

}
