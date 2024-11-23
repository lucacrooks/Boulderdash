public class PathTile extends Tile{
    public PathTile(int x, int y){
        super(x,y,"PATH.png");
    }

    @Override
    public boolean isTraversable(){
        return true;
    }
}
