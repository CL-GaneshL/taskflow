<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class TeamsHaveEmployees extends Model {

    protected $fillable = ['teamId', 'employeeId'];
    public $timestamps = false;

    function getTeamId() {
        return $this->fillable->teamId;
    }

    function getEmployeeId() {
        return $this->fillable->employeeId;
    }

}
