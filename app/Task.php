<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Task extends Model {

    protected $fillable = ['id', 'skill_id', 'project_id'];
    public $timestamps = false;

    function getId() {
        return $this->fillable->id;
    }

    function getSkillId() {
        return $this->fillable->skill_id;
    }

    function getProjectId() {
        return $this->fillable->allocation_id;
    }

}
