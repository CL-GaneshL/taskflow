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
            $table->boolean('morning_shift');
            $table->boolean('afternoon_shift');

            // -----------------------------------------------------
            // - unique indexes
            // - prevent from 2 identical shifts of the same type to be created  
            // -----------------------------------------------------
            $table->unique(array('type', 'date', 'morning_shift'));
            $table->unique(array('type', 'date', 'afternoon_shift'));
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
