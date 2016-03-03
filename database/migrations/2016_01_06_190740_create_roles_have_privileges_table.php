<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateRolesHavePrivilegesTable extends Migration {

    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up() {
        Schema::create('roles_have_privileges', function (Blueprint $table) {

            $table->integer('role_id')->unsigned();
            $table->integer('privilege_id')->unsigned();

            // ------------------------------------------
            // - indexes
            // ------------------------------------------
            $table->unique(array('role_id', 'privilege_id'));
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down() {
        Schema::drop('roles_have_privileges');
    }

}
