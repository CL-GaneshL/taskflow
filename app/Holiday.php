<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Holiday extends Model {

    // ----------------------------------
    // - fillable attributs
    // ----------------------------------
    protected $fillable = [
        'id',
        'title',
        'employee_id',
        'start_date',
        'end_date'
    ];
    // ----------------------------------
    // - remove timestamps
    // ----------------------------------
    public $timestamps = false;

    // ----------------------------------
    // - getters
    // ----------------------------------
    function getId() {
        return $this->fillable->id;
    }

    function getTitle() {
        return $this->fillable->title;
    }

    function getEmployeeId() {
        return $this->fillable->employee_id;
    }

    function getStartDate() {
        return $this->fillable->start_date;
    }

    function getEndDate() {
        return $this->fillable->end_date;
    }

}
