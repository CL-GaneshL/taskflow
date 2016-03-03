<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreatePrivilegesTable extends Migration {

    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up() {

        Schema::create('privileges', function (Blueprint $table) {

            // primary key ------------------
            $table->increments('id');
            // ------------------------------

            $table->enum('privilege_name', array(
                // ------------------------------
                // Skill privileges
                // ------------------------------
                'READ_SKILL_PRIVILEGE',
                'REMOVE_SKILL_PRIVILEGE',
                'ADD_SKILL_PRIVILEGE',
                'CLOSE_SKILL_PRIVILEGE',
                'CREATE_SKILL_PRIVILEGE',
                // ------------------------------
                // Project privileges
                // ------------------------------
                'READ_PROJECT_PRIVILEGE',
                'REMOVE_PROJECT_PRIVILEGE',
                'ADD_PROJECT_PRIVILEGE',
                'CLOSE_PROJECT_PRIVILEGE',
                'CREATE_PROJECT_PRIVILEGE',
                // ------------------------------
                // Project Template privileges
                // ------------------------------
                'READ_PROJECT_TEMPLATE_PRIVILEGE',
                'REMOVE_PROJECT_TEMPLATE_PRIVILEGE',
                'ADD_PROJECT_TEMPLATE_PRIVILEGE',
                'CLOSE_PROJECT_TEMPLATE_PRIVILEGE',
                'CREATE_PROJECT_TEMPLATE_PRIVILEGE',
                // ------------------------------
                // 
                // ------------------------------
                'READ_PROFILE_PRIVILEGE',
                'UPDATE_PROFILE_PRIVILEGE',
                'CREATE_SKILL_PRIVILEGE',
                    )
            );

            $table->string('designation');

            // indexes ----------------------
            $table->unique('privilege_name');
            // ------------------------------
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down() {
        Schema::drop('privileges');
    }

}
