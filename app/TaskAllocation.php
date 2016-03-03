<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class TaskAllocation extends Model {

    protected $fillable = [
        'id',
        'task_id',
        'employee_id',
        'start_date',
        'completion',
        'nb_products_completed',
        'completed',
        'duration'
    ];
    public $timestamps = false;

    function getId() {
        return $this->fillable->id;
    }

    function getTaskId() {
        return $this->fillable->task_id;
    }

    function getEmployeeId() {
        return $this->fillable->employee_id;
    }

    function getStartDate() {
        return $this->fillable->start_date;
    }

    function getCompletion() {
        return $this->fillable->completion;
    }

    function getNbProductsCompleted() {
        return $this->fillable->nb_products_completed;
    }

    function getCompleted() {
        return $this->fillable->completed;
    }

    function getDuration() {
        return $this->fillable->duration;
    }

}
