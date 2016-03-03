<?php

use Illuminate\Database\Seeder;

class PrivilegesTableSeeder extends Seeder {

    public function run() {
        DB::table('privileges')->insert([
            // ------------------------------
            // Skill privileges
            // ------------------------------
            [
                // id = 1
                'privilege_name' => "READ_SKILL_PRIVILEGE",
                'designation' => "Read Employee's Skill privilege"
            ],
            [
                // id = 2
                'privilege_name' => "REMOVE_SKILL_PRIVILEGE",
                'designation' => "Remove Employee's Skill privilege"
            ],
            [
                // id = 3
                'privilege_name' => "ADD_SKILL_PRIVILEGE",
                'designation' => "Add Employee's Skill privilege"
            ],
            [
                // id = 4
                'privilege_name' => "CLOSE_SKILL_PRIVILEGE",
                'designation' => "Close Employee's Skill privilege"
            ],
            // ------------------------------
            // 
            // ------------------------------   
            [
                // id = 5
                'privilege_name' => "READ_PROFILE_PRIVILEGE",
                'designation' => "Read Employee Profile privilege"
            ],
            [
                // id = 6
                'privilege_name' => "UPDATE_PROFILE_PRIVILEGE",
                'designation' => "Update Employee Profile privilege"
            ]
        ]);
    }

}
