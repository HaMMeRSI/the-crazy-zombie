package com.sagi.zombie.addons;

public class Vector2
{
    private float X;
    private float Y;

    public Vector2()
    {
        this.X = 0;
        this.Y = 0;
    }
    
    public Vector2(float X, float Y)
    {
        this.X = X;
        this.Y = Y;
    }
    
    public float getX() {
		return X;
	}

	public void setX(float x) {
		X = x;
	}

	public float getY() {
		return Y;
	}

	public void setY(float y) {
		Y = y;
	}

	public void setCords(float x, float y)
	{
		this.X = x;
		this.Y = y;
	}
	public void setCords(Vector2 v)
	{
		this.X = v.X;
		this.Y = v.Y;
	}
	
	/**
	 * Copy the vector
	 * @return new Vector2 object with same x and y
	 */
	public Vector2 Clone()
    {
        return new Vector2(this.X, this.Y);
    }
    /**
     * adds current vector with other
     * v1.add(v2);
     * @param v added vector
     */
    public void Add(Vector2 v)
    {
        this.X += v.X;
        this.Y += v.Y;
    }
    
    public void Add(float n)
    {
    	this.X += n;
    	this.Y += n;
    }
    /**
     * static method to add 2 vectors
     * Vector2.add(v1,v2);
     * @param v1 vector 1
     * @param v2 vector 2
     * @return new vector with the sum
     */
    public static Vector2 Add(Vector2 v1, Vector2 v2)
    {
        return new Vector2(v1.X + v2.X, v1.Y + v2.Y);
    }
    /**
     * static method to add 1 vector with number
     * @param v1 vector 1
     * @param N float number
     * @return new vector with the sum
     */
    public static Vector2 Add(Vector2 v1, float N)
    {
        return new Vector2(v1.X + N, v1.Y + N);
    }
    
    public void Sub(Vector2 p)
    {
        this.X -= p.X;
        this.Y -= p.Y;
    }
    
    public static Vector2 Sub(Vector2 v1, float N)
    {
        return new Vector2(v1.X - N, v1.Y - N);
    }
    
    public static Vector2 Sub(Vector2 v1, Vector2 v2)
    {
        return new Vector2(v1.X - v2.X, v1.Y - v2.Y);
    }
    
    public void Mul(float N)
    {
        this.X *= N;
        this.Y *= N;
    }
    
    public static Vector2 Mul(Vector2 v1, float N)
    {
        return new Vector2(v1.X * N, v1.Y * N);
    }

	public static Vector2 Mul(Vector2 v1, Vector2 v2) {
		return new Vector2(v1.X * v2.X, v1.Y * v2.Y);
	}
    
    public void Div(float N)
    {
        this.X /= N;
        this.Y /= N;
    }
    
    public static Vector2 Div(Vector2 v1, float N)
    {
        return new Vector2(v1.X / N, v1.Y / N);
    }
    
    public float Mag()
    {
        return (float)Math.sqrt(this.X * this.X + this.Y * this.Y);
    }
    
    public void SetMag(double mag)
    {
        this.X = (float)Math.sqrt(mag * mag - this.Y * this.Y);
        this.Y = (float)Math.sqrt(mag * mag - this.X * this.X);
    }
    
    public float GetDistance(Vector2 V1)
    {
        return (float)Math.sqrt(Math.pow(X - V1.X, 2) + Math.pow(Y - V1.Y, 2));
    }
    
    public void Norm()
    {
        float m = Mag();
        if (m != 0)
            Div(m);
    }
    
    public void Limit(float max)
    {
        if (Mag() > max)
        {
            Norm();
            Mul(max);
        }
    }
    
    public Vector2 getVecAsInt()
    {
    	return new Vector2((int)X, (int)Y);
    }
    
    public boolean equals(Object obj)
	{
		if(obj == null || !(obj instanceof Vector2))
			return false;
		Vector2 v2 = (Vector2)obj;
		return (v2.getX() == X && v2.getY() == Y);
	}
    
    public String toString() 
    {
        return this.X + ", " + this.Y;
    }
}

