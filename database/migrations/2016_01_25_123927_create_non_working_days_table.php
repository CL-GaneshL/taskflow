<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateNonWorkingDaysTable extends Migration {

    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up() {
        Schema::create('non_working_days', function (Blueprint $table) {

            // primary key ------------------
            $table->increments('id');
            // ------------------------------

            $table->string('title');
            $table->enum('type', array('WEEKEND', 'NON-WORKING'));
            $table->date('date');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down() {
        Schema::drop('non_working_days');
    }

}
