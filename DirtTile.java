public class DirtTile extends Tile{

    private boolean compacted;

    public DirtTile(int x, int y){
        super(x,y,"DIRT.png");
        this.compacted = false; // default state where dirt is loose
    }

    public void compact() {
        this.compacted = true; // the dirt is now compact
    }

    public boolean isCompacted() {
        return compacted;
    }

    @Override
    public boolean isTraversable(){
        return true;
    }
}
