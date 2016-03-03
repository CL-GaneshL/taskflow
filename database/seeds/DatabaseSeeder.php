<?php

use Illuminate\Database\Seeder;
use Illuminate\Database\Eloquent\Model;

class DatabaseSeeder extends Seeder {

    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run() {

        Model::unguard();

        // ------------------------------------------------------
        // - employees
        // ------------------------------------------------------
        $this->call(EmployeesTableSeeder::class);

        // ------------------------------------------------------
        // - authentication
        // ------------------------------------------------------
        $this->call(PrivilegesTableSeeder::class);
        $this->call(RolesTableSeeder::class);
        $this->call(RolesHavePrivilegesTableSeeder::class);
        $this->call(UsersTableSeeder::class);

        // ------------------------------------------------------
        // - skills
        // ------------------------------------------------------
        $this->call(SkillsTableSeeder::class);
        $this->call(EmployeesHaveSkillsTableSeeder::class);

        // ------------------------------------------------------
        // - teams
        // ------------------------------------------------------
        $this->call(TeamsTableSeeder::class);
        $this->call(TeamsHaveEmployeesTableSeeder::class);

        // ------------------------------------------------------
        // - projects
        // ------------------------------------------------------
        $this->call(ProjectTemplatesTableSeeder::class);
        $this->call(ProjectTemplatesHaveSkillsTableSeeder::class);
        $this->call(ProjectsTableSeeder::class);
        
//        $this->call(TasksTableSeeder::class);
        
        // ------------------------------------------------------
        // - non working days
        // ------------------------------------------------------ 
        $this->call(NonWorkingDaysTableSeeder::class);
        $this->call(HolidaysTableSeeder::class);
        // ------------------------------------------------------

        Model::reguard();
    }

}
