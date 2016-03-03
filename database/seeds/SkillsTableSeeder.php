<?php

use Illuminate\Database\Seeder;

class SkillsTableSeeder extends Seeder {

    public function run() {
        DB::table('skills')->insert([
            [
                'reference' => "3DMS",
                'designation' => "3D Model - Simple",
                'duration' => 1 * 60  // 1 hours
            ],
            [
                'reference' => "3DMM",
                'designation' => "3D Model - Medium",
                'duration' => 1 * 60 + 15 // 1h 15 mins
            ],
            [
                'reference' => "3DMC",
                'designation' => "3D Model - Complex",
                'duration' => 2 * 60 // 2 hours
            ],
            [
                'reference' => "RenSM",
                'designation' => "Render, Clean-up & Re-size - Simple & Medium",
                'duration' => 1 * 60 + 30 // 1.30 hours
            ],
            [
                'reference' => "RenC",
                'designation' => "Render, Clean-up & Re-size - Complex",
                'duration' => 1 * 60 + 45 // 1h 45 mins
            ],
            [
                'reference' => "CORR",
                'designation' => "Correction",
                'duration' => 2 * 60// 2 hours
            ],
            [
                'reference' => "PTSHOOT",
                'designation' => "Photoshoot",
                'duration' => 1 * 60 + 30 // 1.30 hours
            ],
            [
                'reference' => "GS/CUP",
                'designation' => "Gold / Silver clean up",
                'duration' => 1 * 60 + 45 // 1h 45 mins
            ],
            [
                'reference' => "N3DRen",
                'designation' => "Old 3D Render to New 3D Render",
                'duration' => 1 * 60  // 1 hours
            ],
            [
                'reference' => "GemCol",
                'designation' => "Gem stone color Correction",
                'duration' => 1 * 60 + 30 // 1.30 hours
            ],
            [
                'reference' => "Y2WGOLD",
                'designation' => "Yellow gold to white gold - CAD Edit/Setup & Cleanup",
                'duration' => 2 * 60  // 2 hours
            ],
            [
                'reference' => "NewGem",
                'designation' => "New Gem stone R&D",
                'duration' => 2 * 60  // 2 hours
            ],
            [
                'reference' => "VTORen",
                'designation' => "VTO Renders",
                'duration' => 1 * 60  // 1 hours
            ],
            [
                'reference' => "2dTo3D",
                'designation' => "2D to 3D",
                'duration' => 1 * 60 + 30 // 1.30 hours
            ],
            [
                'reference' => "MkgVID",
                'designation' => "Marketing Video",
                'duration' => 2 * 60  // 2 hours
            ],
            [
                'reference' => "PVID",
                'designation' => "Product videos",
                'duration' => 1 * 60  // 1 hours
            ],
            [
                'reference' => "MkgRen",
                'designation' => "Marketing Renders",
                'duration' => 1 * 60 + 30 // 1.30 hours
            ],
        ]);
    }

}
