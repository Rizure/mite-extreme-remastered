package net.xiaoyu233.mitemod.miteite.network;

import net.minecraft.Connection;
import net.minecraft.Minecraft;
import net.minecraft.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class SPacketUpdateForgingTableModeState extends Packet {
   int state;
   int x;
   int y;
   int z;
   public SPacketUpdateForgingTableModeState(){
   }
   public SPacketUpdateForgingTableModeState(int x, int y, int z, int state){
      this.x = x;
      this.y = y;
      this.z = z;
      this.state = state;
   }
   @Override
   public void readPacketData(DataInput dataInput) throws IOException {
      this.x = dataInput.readInt();
      this.y = dataInput.readInt();
      this.z = dataInput.readInt();
      this.state = dataInput.readInt();
   }

   @Override
   public void writePacketData(DataOutput dataOutput) throws IOException {
      dataOutput.writeInt(this.x);
      dataOutput.writeInt(this.y);
      dataOutput.writeInt(this.z);
      dataOutput.writeInt(this.state);
   }
   public int getState(){
      return this.state;
   }
   @Override
   public void processPacket(Connection connection) {
      connection.handleUpdateForgingTableModeState(this);
   }

   @Override
   public int getPacketSize() {
      return 16;
   }

   public int getX() { return x; }
   public int getY() { return y; }
   public int getZ() { return z; }
}
