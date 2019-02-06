package frc.robot.Utilities.PID;

import frc.robot.Utilities.Dashboard;

/**
* General purpose PID loop.
* @author Gowan Rowland
* @version 2/15/2018
*/
public abstract class PID {
  // Constants
	protected double Kp;
	protected double Ki;
	protected double Kd;
  // error data
  protected double error;
	protected double prevError;

  protected double derivative;

	public double setpoint;
	protected double output;

  // integral data
  protected double integral;
  protected double iRange = 0;

  // Rate limiting
  protected double sampleRate; // Htz (iterations per second)
  protected double rateAccumulator;

  // error tolerances
  protected double errorTolerance;
  protected int nIterationsInTolerance;


	public PID( double sampleRate, double errorTolerance ){
		setpoint = 0;
	    integral = 0;
	    nIterationsInTolerance = 0;
	    this.sampleRate = sampleRate;
	    this.errorTolerance = errorTolerance;
}
  public abstract double getValue();

  public void flush(){ // flushes any earlier values for controller
    output = 0;
    derivative = 0;
    prevError = 0;
    error = 0;
    integral = 0;
  }
  public double getError(){
	  return error;
  }
	public void setIRange(double i) {
    iRange = i;
	}

	public void setSetpoint( double s ){
		setpoint = s;
		integral = 0;
	}

	public void setPID( double p, double i, double d ) {
		this.Kp = p;
		this.Ki = i;
		this.Kd = d;
	}

  public double getTimeInTolerance() {
    return (1/sampleRate) * nIterationsInTolerance;
  }
  public void reset(){
	  nIterationsInTolerance = 0;
  }
  protected double computeError() {
    return setpoint - getValue();
  }
  private void computePID(double dt){
    error =  computeError();// compute system error

    derivative = (error - prevError) / dt; // computer derivative
    
    prevError = error;

		if(Math.abs(error) < iRange)
		{
			integral += (1/Ki) * error * dt;
		}
		Dashboard.send("D value", Kd * derivative);
	    Dashboard.send("P value", Kp * error);
	    Dashboard.send("I value", integral);
		output = ( Kp * error ) + ( integral ) + ( Kd * derivative  );
  }

  private void computeErrorTolerance() {
    if (Math.abs(error) < errorTolerance) {
      nIterationsInTolerance++;
    } else {
      nIterationsInTolerance = 0;
    }
  }

	public void update( double dt ){
    if (rateAccumulator >= (1.0/sampleRate)) {
      computePID(dt);
      computeErrorTolerance();
      rateAccumulator = 0;
    }
    rateAccumulator += dt;
	}

	public double get() {
		return output;
	}
}
