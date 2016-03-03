<?php

use Illuminate\Database\Seeder;

class RolesTableSeeder extends Seeder {

    public function run() {

        DB::table('roles')->insert([
            [
                'role_name' => "ADMIN_ROLE",
                'designation' => "Taskflow Webapp Administrator role"
            ],
            [
                'role_name' => "PROJECT_MANAGER_ROLE",
                'designation' => "Taskflow Webapp Project Manager role"
            ],
            [
                'role_name' => "TEAM_LEAD_ROLE",
                'designation' => "Taskflow Webapp Team Lead role"
            ],
            [
                'role_name' => "EMPLOYEE_ROLE",
                'designation' => "Taskflow Webapp Employee role"
            ]
        ]);
    }

}
