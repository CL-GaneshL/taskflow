<?php

namespace App\Exceptions;

use Exception;
use Illuminate\Database\Eloquent\ModelNotFoundException;
use Symfony\Component\HttpKernel\Exception\HttpException;
use Symfony\Component\HttpKernel\Exception\NotFoundHttpException;
use Illuminate\Foundation\Exceptions\Handler as ExceptionHandler;

class Handler extends ExceptionHandler {

    /**
     * A list of the exception types that should not be reported.
     *
     * @var array
     */
    protected $dontReport = [
        HttpException::class,
        ModelNotFoundException::class,
    ];

    /**
     * Report or log an exception.
     *
     * This is a great spot to send exceptions to Sentry, Bugsnag, etc.
     *
     * @param  \Exception  $e
     * @return void
     */
    public function report(Exception $e) {
        return parent::report($e);
    }

    /**
     * Render an exception into an HTTP response.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \Exception  $e
     * @return \Illuminate\Http\Response
     */
    public function render($request, Exception $e) {

        if ($e instanceof ModelNotFoundException) {
            $e = new NotFoundHttpException($e->getMessage(), $e);
        }

        if ($this->isHttpException($e)) {
            switch ($e->getStatusCode()) {

                /*
                  |------------------------------------------------------
                  | invalid routes
                  |------------------------------------------------------
                  |
                  | all invalid routes will be redirected to
                  | the frontend this allows angular to route them
                  |
                 */

                case 404:
                    $path = \Config::get('caratlane.constants.CARATLANE_TASKFLOW_PATH');
                    return redirect()->guest($path);

//                case 500:
//                    return Response::json(
//                                    [
//                                'statusText' => class_basename($e) . ' in ' . basename($e->getFile()) . ' line ' . $e->getLine() . ': ' . $e->getMessage(),
//                                    ], 500);

                default:
                    return $this->renderHttpException($e);
            }
        } else {
            return parent::render($request, $e);
        }
    }

}
