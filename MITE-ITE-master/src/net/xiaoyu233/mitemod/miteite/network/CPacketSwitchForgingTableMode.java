package net.xiaoyu233.mitemod.miteite.network;

import net.minecraft.Connection;
import net.minecraft.Packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class CPacketSwitchForgingTableMode extends Packet {
   public CPacketSwitchForgingTableMode() {
   }
   @Override
   public void readPacketData(DataInput dataInput) throws IOException {
   }

   @Override
   public void writePacketData(DataOutput dataOutput) throws IOException {
   }
   @Override
   public void processPacket(Connection connection) {
      connection.processSwitchForgingTableModePacket(this);
   }

   @Override
   public int getPacketSize() {
      return 4;
   }
}
