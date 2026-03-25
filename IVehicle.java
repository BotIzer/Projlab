public interface IVehicle 
{
    public void Move();
    public void Stop();
    public void Slipping();
    public void SetRoute(Intersection start, Intersection end);
}
