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
        'start_morning_shift',
        'start_afternoon_shift',
        'end_date',
        'end_morning_shift',
        'end_afternoon_shift'
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

    function getStartMorningShift() {
        return $this->fillable->start_morning_shift;
    }

    function getStartAfternoonShift() {
        return $this->fillable->start_afternoon_shift;
    }
    
      function getEndDate() {
        return $this->fillable->end_date;
    }

    function getEndMorningShift() {
        return $this->fillable->end_morning_shift;
    }

    function getEndAfternoonShift() {
        return $this->fillable->end_afternoon_shift;
    }
    
}
