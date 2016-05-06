<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateTaskAllocationsTable extends Migration {

    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up() {
        Schema::create('task_allocations', function (Blueprint $table) {

            // primary key ------------------
            $table->increments('id');
            // ------------------------------

            $table->integer('task_id')->unsigned();
            $table->integer('employee_id')->unsigned();
            $table->dateTime('start_date');
            // time is which the allocation was completed
            $table->integer('completion')->default(0);
            // number of products done
            $table->integer('nb_products_completed')->default(0);
            // number of planned products 
            $table->decimal('nb_products_planned', 4, 2)->default(0.00);
            // when the allocation is completed
            $table->boolean('completed')->default(0);
            $table->integer('duration')->default(0);

            // ------------------------------------------
            // - foreign keys
            // ------------------------------------------
            $table->foreign('task_id')->references('id')->on('tasks');
            $table->foreign('employee_id')->references('id')->on('employees');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down() {
        Schema::drop('task_allocations');
    }

}
