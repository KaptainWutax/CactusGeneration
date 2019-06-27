package kaptainwutax.cactusgen;

public class CactusGen {

    public static void main(String[] args) {
        int floorLevel = 62;

        CactusSimulation cs = new CactusSimulation(CactusSimulation.DESERT, floorLevel);
        cs.populate(1235L);

        for(int x = 0; x < 32; x++) {
            for(int z = 31; z >= 0; z--) {
                int height = cs.heightMap[x + z * 32] - floorLevel;
                if(height == 0)System.out.print(" - ");
                else System.out.print("[" + height + "]");
            }
            System.out.print('\n');
        }

        System.out.println("Tallest cactus found is " + (cs.heightMap[cs.currentHighestPos] - floorLevel) + ".");
    }

}
