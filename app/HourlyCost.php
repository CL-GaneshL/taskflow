<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class HourlyCost extends Model {

    protected $fillable = ['cost'];
    public $timestamps = false;

    function getCost() {
        return $this->fillable->cost;
    }

}
