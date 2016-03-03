<?php

use Illuminate\Database\Seeder;

class RolesHavePrivilegesTableSeeder extends Seeder {

    public function run() {

        DB::table('roles_have_privileges')->insert([
            // =================================================================
            // - admin role
            // =================================================================
            // ------------------------------
            // Skill privileges
            // ------------------------------
            [
                'role_id' => 1,
                'privilege_id' => 1
            ],
            [
                'role_id' => 1,
                'privilege_id' => 2
            ],
            [
                'role_id' => 1,
                'privilege_id' => 3
            ],
            [
                'role_id' => 1,
                'privilege_id' => 4
            ],
            [
                'role_id' => 1,
                'privilege_id' => 5
            ],
            // ------------------------------
            // Project privileges
            // ------------------------------
            [
                'role_id' => 1,
                'privilege_id' => 6
            ],
            [
                'role_id' => 1,
                'privilege_id' => 7
            ],
            [
                'role_id' => 1,
                'privilege_id' => 8
            ],
            [
                'role_id' => 1,
                'privilege_id' => 9
            ],
            [
                'role_id' => 1,
                'privilege_id' => 10
            ],
            // ------------------------------
            // Project Template privileges
            // ------------------------------
            [
                'role_id' => 1,
                'privilege_id' => 11
            ],
            [
                'role_id' => 1,
                'privilege_id' => 12
            ],
            [
                'role_id' => 1,
                'privilege_id' => 13
            ],
            [
                'role_id' => 1,
                'privilege_id' => 14
            ],
            [
                'role_id' => 1,
                'privilege_id' => 15
            ],
            // =================================================================
            // - Project Manager role
            // =================================================================
            // ------------------------------
            // Skill privileges
            // ------------------------------
            [
                'role_id' => 2,
                'privilege_id' => 1
            ],
            [
                'role_id' => 2,
                'privilege_id' => 2
            ],
            [
                'role_id' => 2,
                'privilege_id' => 3
            ],
            [
                'role_id' => 2,
                'privilege_id' => 4
            ],
            [
                'role_id' => 2,
                'privilege_id' => 5
            ],
            // ------------------------------
            // Project privileges
            // ------------------------------
            [
                'role_id' => 2,
                'privilege_id' => 6
            ],
            [
                'role_id' => 2,
                'privilege_id' => 7
            ],
            [
                'role_id' => 2,
                'privilege_id' => 8
            ],
            [
                'role_id' => 2,
                'privilege_id' => 9
            ],
            [
                'role_id' => 2,
                'privilege_id' => 10
            ],
            // ------------------------------
            // Project Template privileges
            // ------------------------------
            [
                'role_id' => 2,
                'privilege_id' => 11
            ],
            [
                'role_id' => 2,
                'privilege_id' => 12
            ],
            [
                'role_id' => 2,
                'privilege_id' => 13
            ],
            [
                'role_id' => 2,
                'privilege_id' => 14
            ],
            [
                'role_id' => 2,
                'privilege_id' => 15
            ],
            // =================================================================
            // - Team Lead role
            // =================================================================
            // ------------------------------
            // Skill privileges
            // ------------------------------
            [
                'role_id' => 3,
                'privilege_id' => 1 // READ_SKILL_PRIVILEGE
            ],
            // ------------------------------
            // Project privileges
            // ------------------------------
            [
                'role_id' => 3,
                'privilege_id' => 6 // READ_PROJECT_PRIVILEGE
            ],
            // ------------------------------
            // Project Template privileges
            // ------------------------------
            [
                'role_id' => 3,
                'privilege_id' => 11 // READ_PROJECT_TEMPLATE_PRIVILEGE
            ],
            // =================================================================
            // - employee role
            // =================================================================
            // ------------------------------
            // Skill privileges
            // ------------------------------
            [
                'role_id' => 4,
                'privilege_id' => 1 // READ_SKILL_PRIVILEGE
            ],
            // ------------------------------
            // Project privileges
            // ------------------------------
            [
                'role_id' => 4,
                'privilege_id' => 6 // READ_PROJECT_PRIVILEGE
            ],
            // ------------------------------
            // Project Template privileges
            // ------------------------------
            [
                'role_id' => 4,
                'privilege_id' => 11 // READ_PROJECT_TEMPLATE_PRIVILEGE
            ]
        ]);
    }

}
