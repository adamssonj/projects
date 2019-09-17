###### Task 1.1:

Let's consider a kinematic bicycle model as described in Link:

* ​x' =v cos⁡θ
* y˙=v sin⁡θ
* θ˙= v/L tan⁡δ

The system state is described by **x** = [x,y,θ]

- x,y coordinates and θ is the orientation of the robot

System inputs should probably be the vector **u** = [v, δ]'
I.e. velocity and turn angle.
- With new values for v and delta we can calculate new states for every time interval.


So, an equilibrium point f(x0, u0) could for this function be
f(x, [0,0]). Since cosθ and sinθ is limited in the range [-1,1] we know that x' and y' = 0 with v = 0. But since tanθ is in the range (-∞,∞) we need to constrain δ as well to be sure θ' = 0.

So with **x** = [x,y,θ] and **u** = [v, δ]

We write

* **u**(t) = **u0** + **Δu**(t)
* **x**(t) = **x0** + **Δx**(t)


Then
* **Δx**(t)' = (df/dx)(**x0**,**u0**)* **Δx**(t) + (df/du)(**x0**,**u0**)* **Δu**(t)
  * Since x0' = 0, **x**' = f(x0 + Δx, u0+Δu) and f(**x0**,**u0**) = 0
  * So, we Taylor expanded 

Where
 * (df/dx)(**x0**,**u0**)* **Δx**(t) = [0,0,-v*sinθ;0,0,v*cosθ;0,0,0];
    * With u0=[0,0] => [0,0,0;0,0,0;0,0,0]


 * (df/du)(**x0**,**u0**)* **Δu**(t) = [cosθ, 0; sinθ, 0; (1/L)\*tanδ, (v/L)\*(1/cos^2(δ))]
    * With u0 =[0,0] => [cosθ,0;sinθ,0;0,0]


So if we describe the system as:

* **Δx**(t)' =A\* **Δx**(t)  +B* **Δu**(t)

Gives, A the zero matrix and B =

|cos θ  | 0 |
|-------|---|
|sin θ  |  0|
|0      |  0|

Kind of makes sense, only the orientation of the vehicle matters when we are close to the equilibrium point and the turn angle is close to 0. If we change change the turn angle in the equilibrium point, e.g u0 = [0,pi/4]. We get

|cos θ  | 0 |
|-------|---|
|sin θ  |  0|
|1/L    |  0|

And thus how much we have already turned impacts the new orientation more. Not much at all though since v ≈ 0
