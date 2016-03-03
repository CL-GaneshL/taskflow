<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class EmployeesHaveSkills extends Model {

    protected $fillable = ['employeeId', 'skillId'];
    public $timestamps = false;

    function getEmployeeId() {
        return $this->fillable->employeeId;
    }

    function getSkillId() {
        return $this->fillable->skillId;
    }

}
