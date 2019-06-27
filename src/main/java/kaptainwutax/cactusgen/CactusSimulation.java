package kaptainwutax.cactusgen;

import java.util.Arrays;
import java.util.Random;

public class CactusSimulation {

    public static int DESERT = 10;
    public static int BADLANDS = 5;

    public int[] heightMap = new int[32 * 32];
    public int currentHighestPos = -1;

    private int count;
    private int floorLevel;

    public CactusSimulation(int count, int floorLevel) {
        this.count = count;
        this.floorLevel = floorLevel;

        for(int i = 0; i < this.heightMap.length; i++) {
            this.heightMap[i] = this.floorLevel;
        }
    }

    public void populate(long seed) {
        Random random = new Random(seed ^ 0x5DEECE66DL);

        for(int i = 0; i < this.count; i++) {
            int initialPosX = random.nextInt(16) + 8;
            int initialPosZ = random.nextInt(16) + 8;
            int terrainHeight = (this.heightMap[initialPosX + initialPosZ * 32] + 1) * 2;

            if(terrainHeight > 0) {
                int initialPosY = random.nextInt(terrainHeight);
                this.generateCactus(random, initialPosX, initialPosY, initialPosZ);
            }
        }
    }

    public void generateCactus(Random random, int initialPosX, int initialPosY, int initialPosZ) {
        for(int i = 0; i < 10; i++) {
            int posX = initialPosX + random.nextInt(8) - random.nextInt(8);
            int posY = initialPosY + random.nextInt(4) - random.nextInt(4);
            int posZ = initialPosZ + random.nextInt(8) - random.nextInt(8);

            if(!isAir(posX, posY, posZ))continue;

            int offset = 1 + random.nextInt(random.nextInt(3) + 1);

            int posMap = posX + posZ * 32;

            for(int j = 0; j < offset; j++) {
                if(isAir(posX, posY + j - 1, posZ))continue;
                if(!isAir(posX + 1, posY + j, posZ))continue;
                if(!isAir(posX - 1, posY + j, posZ))continue;
                if(!isAir(posX, posY + j, posZ + 1))continue;
                if(!isAir(posX, posY + j, posZ - 1))continue;

                this.heightMap[posMap]++;

                if(this.currentHighestPos == -1 || this.heightMap[currentHighestPos] < this.heightMap[posMap]) {
                    this.currentHighestPos = posMap;
                }
            }
        }
    }

    public boolean isAir(int x, int y, int z) {
        int height = this.heightMap[x + z * 32];
        return y > height;
    }

}
