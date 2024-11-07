module UnifiedButterflyUnit(
  input         clock,
  input         reset,
  input         io_start, // @[src/main/scala/UnifiedButterflyUnit.scala 7:14]
  input         io_select, // @[src/main/scala/UnifiedButterflyUnit.scala 7:14]
  input         io_inverse, // @[src/main/scala/UnifiedButterflyUnit.scala 7:14]
  input  [15:0] io_u, // @[src/main/scala/UnifiedButterflyUnit.scala 7:14]
  input  [15:0] io_v, // @[src/main/scala/UnifiedButterflyUnit.scala 7:14]
  input  [15:0] io_w, // @[src/main/scala/UnifiedButterflyUnit.scala 7:14]
  output [15:0] io_x, // @[src/main/scala/UnifiedButterflyUnit.scala 7:14]
  output [15:0] io_y, // @[src/main/scala/UnifiedButterflyUnit.scala 7:14]
  output        io_finish // @[src/main/scala/UnifiedButterflyUnit.scala 7:14]
);
`ifdef RANDOMIZE_REG_INIT
  reg [31:0] _RAND_0;
  reg [31:0] _RAND_1;
  reg [31:0] _RAND_2;
  reg [31:0] _RAND_3;
  reg [31:0] _RAND_4;
  reg [31:0] _RAND_5;
  reg [31:0] _RAND_6;
  reg [31:0] _RAND_7;
  reg [31:0] _RAND_8;
  reg [31:0] _RAND_9;
  reg [31:0] _RAND_10;
  reg [31:0] _RAND_11;
  reg [31:0] _RAND_12;
  reg [31:0] _RAND_13;
  reg [31:0] _RAND_14;
  reg [31:0] _RAND_15;
  reg [31:0] _RAND_16;
  reg [31:0] _RAND_17;
  reg [31:0] _RAND_18;
  reg [31:0] _RAND_19;
  reg [31:0] _RAND_20;
  reg [31:0] _RAND_21;
  reg [31:0] _RAND_22;
  reg [31:0] _RAND_23;
`endif // RANDOMIZE_REG_INIT
  reg  str_del0; // @[src/main/scala/UnifiedButterflyUnit.scala 21:25]
  reg  str_del1; // @[src/main/scala/UnifiedButterflyUnit.scala 22:25]
  reg  str_del2; // @[src/main/scala/UnifiedButterflyUnit.scala 23:25]
  reg  str_del3; // @[src/main/scala/UnifiedButterflyUnit.scala 24:25]
  reg  str_del4; // @[src/main/scala/UnifiedButterflyUnit.scala 25:25]
  reg  str_del5; // @[src/main/scala/UnifiedButterflyUnit.scala 26:25]
  reg  str_del6; // @[src/main/scala/UnifiedButterflyUnit.scala 27:25]
  reg [15:0] reg0; // @[src/main/scala/UnifiedButterflyUnit.scala 30:21]
  reg [15:0] reg1; // @[src/main/scala/UnifiedButterflyUnit.scala 31:21]
  reg [15:0] reg2; // @[src/main/scala/UnifiedButterflyUnit.scala 32:21]
  reg [15:0] reg_del1; // @[src/main/scala/UnifiedButterflyUnit.scala 35:25]
  reg [15:0] reg_del2; // @[src/main/scala/UnifiedButterflyUnit.scala 36:25]
  reg [15:0] reg_del3; // @[src/main/scala/UnifiedButterflyUnit.scala 37:25]
  reg [15:0] reg_del4; // @[src/main/scala/UnifiedButterflyUnit.scala 38:25]
  reg [15:0] reg_del5; // @[src/main/scala/UnifiedButterflyUnit.scala 39:25]
  reg [31:0] c1; // @[src/main/scala/UnifiedButterflyUnit.scala 42:19]
  reg [31:0] c1_del1; // @[src/main/scala/UnifiedButterflyUnit.scala 43:24]
  reg [31:0] c1_del2; // @[src/main/scala/UnifiedButterflyUnit.scala 44:24]
  reg [15:0] ta; // @[src/main/scala/UnifiedButterflyUnit.scala 46:19]
  reg [31:0] tb; // @[src/main/scala/UnifiedButterflyUnit.scala 47:19]
  reg [15:0] c2; // @[src/main/scala/UnifiedButterflyUnit.scala 49:19]
  reg [15:0] c3; // @[src/main/scala/UnifiedButterflyUnit.scala 50:19]
  reg [15:0] x_reg; // @[src/main/scala/UnifiedButterflyUnit.scala 52:22]
  reg [15:0] y_reg; // @[src/main/scala/UnifiedButterflyUnit.scala 53:22]
  wire  _T = ~io_select; // @[src/main/scala/UnifiedButterflyUnit.scala 58:20]
  wire  _T_1 = ~io_inverse; // @[src/main/scala/UnifiedButterflyUnit.scala 58:46]
  wire [15:0] _reg1_T_1 = io_u - io_v; // @[src/main/scala/UnifiedButterflyUnit.scala 60:23]
  wire [15:0] _reg1_T_3 = io_u + 16'hd01; // @[src/main/scala/UnifiedButterflyUnit.scala 62:23]
  wire [15:0] _reg1_T_5 = _reg1_T_3 - io_v; // @[src/main/scala/UnifiedButterflyUnit.scala 62:32]
  wire [15:0] _T_5 = io_u + io_v; // @[src/main/scala/UnifiedButterflyUnit.scala 65:17]
  wire [15:0] _reg0_T_3 = _T_5 - 16'hd01; // @[src/main/scala/UnifiedButterflyUnit.scala 66:30]
  wire [31:0] _c1_T = reg1 * reg2; // @[src/main/scala/UnifiedButterflyUnit.scala 83:16]
  wire [44:0] _ta_T = c1 * 13'h13af; // @[src/main/scala/UnifiedButterflyUnit.scala 88:16]
  wire [26:0] _tb_T = {ta, 11'h0}; // @[src/main/scala/UnifiedButterflyUnit.scala 94:16]
  wire [25:0] _tb_T_1 = {ta, 10'h0}; // @[src/main/scala/UnifiedButterflyUnit.scala 94:29]
  wire [26:0] _GEN_34 = {{1'd0}, _tb_T_1}; // @[src/main/scala/UnifiedButterflyUnit.scala 94:23]
  wire [26:0] _tb_T_3 = _tb_T + _GEN_34; // @[src/main/scala/UnifiedButterflyUnit.scala 94:23]
  wire [23:0] _tb_T_4 = {ta, 8'h0}; // @[src/main/scala/UnifiedButterflyUnit.scala 94:42]
  wire [26:0] _GEN_35 = {{3'd0}, _tb_T_4}; // @[src/main/scala/UnifiedButterflyUnit.scala 94:36]
  wire [26:0] _tb_T_6 = _tb_T_3 + _GEN_35; // @[src/main/scala/UnifiedButterflyUnit.scala 94:36]
  wire [26:0] _GEN_36 = {{11'd0}, ta}; // @[src/main/scala/UnifiedButterflyUnit.scala 94:48]
  wire [26:0] _tb_T_8 = _tb_T_6 + _GEN_36; // @[src/main/scala/UnifiedButterflyUnit.scala 94:48]
  wire [31:0] _c2_T_1 = c1_del2 - tb; // @[src/main/scala/UnifiedButterflyUnit.scala 100:20]
  wire [15:0] _c3_T_1 = c2 - 16'hd01; // @[src/main/scala/UnifiedButterflyUnit.scala 106:17]
  wire [15:0] _y_reg_T_1 = reg_del5 - c3; // @[src/main/scala/UnifiedButterflyUnit.scala 119:28]
  wire [15:0] _y_reg_T_3 = reg_del5 + 16'hd01; // @[src/main/scala/UnifiedButterflyUnit.scala 121:28]
  wire [15:0] _y_reg_T_5 = _y_reg_T_3 - c3; // @[src/main/scala/UnifiedButterflyUnit.scala 121:37]
  wire [15:0] _T_12 = reg_del5 + c3; // @[src/main/scala/UnifiedButterflyUnit.scala 124:21]
  wire [15:0] _x_reg_T_3 = _T_12 - 16'hd01; // @[src/main/scala/UnifiedButterflyUnit.scala 125:33]
  wire [31:0] _GEN_21 = {{5'd0}, _tb_T_8}; // @[src/main/scala/UnifiedButterflyUnit.scala 47:19 56:19 94:8]
  wire [20:0] _GEN_37 = reset ? 21'h0 : _ta_T[44:24]; // @[src/main/scala/UnifiedButterflyUnit.scala 46:{19,19}]
  wire [31:0] _GEN_38 = reset ? 32'h0 : _c2_T_1; // @[src/main/scala/UnifiedButterflyUnit.scala 49:{19,19}]
  assign io_x = x_reg; // @[src/main/scala/UnifiedButterflyUnit.scala 134:8]
  assign io_y = y_reg; // @[src/main/scala/UnifiedButterflyUnit.scala 135:8]
  assign io_finish = str_del6; // @[src/main/scala/UnifiedButterflyUnit.scala 136:13]
  always @(posedge clock) begin
    if (reset) begin // @[src/main/scala/UnifiedButterflyUnit.scala 21:25]
      str_del0 <= 1'h0; // @[src/main/scala/UnifiedButterflyUnit.scala 21:25]
    end else begin
      str_del0 <= io_start;
    end
    if (reset) begin // @[src/main/scala/UnifiedButterflyUnit.scala 22:25]
      str_del1 <= 1'h0; // @[src/main/scala/UnifiedButterflyUnit.scala 22:25]
    end else begin
      str_del1 <= str_del0;
    end
    if (reset) begin // @[src/main/scala/UnifiedButterflyUnit.scala 23:25]
      str_del2 <= 1'h0; // @[src/main/scala/UnifiedButterflyUnit.scala 23:25]
    end else begin
      str_del2 <= str_del1;
    end
    if (reset) begin // @[src/main/scala/UnifiedButterflyUnit.scala 24:25]
      str_del3 <= 1'h0; // @[src/main/scala/UnifiedButterflyUnit.scala 24:25]
    end else begin
      str_del3 <= str_del2;
    end
    if (reset) begin // @[src/main/scala/UnifiedButterflyUnit.scala 25:25]
      str_del4 <= 1'h0; // @[src/main/scala/UnifiedButterflyUnit.scala 25:25]
    end else begin
      str_del4 <= str_del3;
    end
    if (reset) begin // @[src/main/scala/UnifiedButterflyUnit.scala 26:25]
      str_del5 <= 1'h0; // @[src/main/scala/UnifiedButterflyUnit.scala 26:25]
    end else begin
      str_del5 <= str_del4;
    end
    if (reset) begin // @[src/main/scala/UnifiedButterflyUnit.scala 27:25]
      str_del6 <= 1'h0; // @[src/main/scala/UnifiedButterflyUnit.scala 27:25]
    end else begin
      str_del6 <= str_del5;
    end
    if (reset) begin // @[src/main/scala/UnifiedButterflyUnit.scala 30:21]
      reg0 <= 16'h0; // @[src/main/scala/UnifiedButterflyUnit.scala 30:21]
    end else if (~io_select & ~io_inverse) begin // @[src/main/scala/UnifiedButterflyUnit.scala 58:59]
      if (_T_5 > 16'hd01) begin // @[src/main/scala/UnifiedButterflyUnit.scala 65:34]
        reg0 <= _reg0_T_3; // @[src/main/scala/UnifiedButterflyUnit.scala 66:14]
      end else begin
        reg0 <= _T_5; // @[src/main/scala/UnifiedButterflyUnit.scala 68:14]
      end
    end else begin
      reg0 <= io_u; // @[src/main/scala/UnifiedButterflyUnit.scala 71:12]
    end
    if (reset) begin // @[src/main/scala/UnifiedButterflyUnit.scala 31:21]
      reg1 <= 16'h0; // @[src/main/scala/UnifiedButterflyUnit.scala 31:21]
    end else if (~io_select & ~io_inverse) begin // @[src/main/scala/UnifiedButterflyUnit.scala 58:59]
      if (io_u > io_v) begin // @[src/main/scala/UnifiedButterflyUnit.scala 59:25]
        reg1 <= _reg1_T_1; // @[src/main/scala/UnifiedButterflyUnit.scala 60:14]
      end else begin
        reg1 <= _reg1_T_5; // @[src/main/scala/UnifiedButterflyUnit.scala 62:14]
      end
    end else begin
      reg1 <= io_v; // @[src/main/scala/UnifiedButterflyUnit.scala 72:12]
    end
    if (reset) begin // @[src/main/scala/UnifiedButterflyUnit.scala 32:21]
      reg2 <= 16'h0; // @[src/main/scala/UnifiedButterflyUnit.scala 32:21]
    end else if (_T_1) begin // @[src/main/scala/UnifiedButterflyUnit.scala 75:34]
      reg2 <= io_w; // @[src/main/scala/UnifiedButterflyUnit.scala 76:12]
    end else begin
      reg2 <= 16'hce7; // @[src/main/scala/UnifiedButterflyUnit.scala 78:12]
    end
    if (reset) begin // @[src/main/scala/UnifiedButterflyUnit.scala 35:25]
      reg_del1 <= 16'h0; // @[src/main/scala/UnifiedButterflyUnit.scala 35:25]
    end else begin
      reg_del1 <= reg0;
    end
    if (reset) begin // @[src/main/scala/UnifiedButterflyUnit.scala 36:25]
      reg_del2 <= 16'h0; // @[src/main/scala/UnifiedButterflyUnit.scala 36:25]
    end else begin
      reg_del2 <= reg_del1;
    end
    if (reset) begin // @[src/main/scala/UnifiedButterflyUnit.scala 37:25]
      reg_del3 <= 16'h0; // @[src/main/scala/UnifiedButterflyUnit.scala 37:25]
    end else begin
      reg_del3 <= reg_del2;
    end
    if (reset) begin // @[src/main/scala/UnifiedButterflyUnit.scala 38:25]
      reg_del4 <= 16'h0; // @[src/main/scala/UnifiedButterflyUnit.scala 38:25]
    end else begin
      reg_del4 <= reg_del3;
    end
    if (reset) begin // @[src/main/scala/UnifiedButterflyUnit.scala 39:25]
      reg_del5 <= 16'h0; // @[src/main/scala/UnifiedButterflyUnit.scala 39:25]
    end else begin
      reg_del5 <= reg_del4;
    end
    if (reset) begin // @[src/main/scala/UnifiedButterflyUnit.scala 42:19]
      c1 <= 32'h0; // @[src/main/scala/UnifiedButterflyUnit.scala 42:19]
    end else begin
      c1 <= _c1_T;
    end
    if (reset) begin // @[src/main/scala/UnifiedButterflyUnit.scala 43:24]
      c1_del1 <= 32'h0; // @[src/main/scala/UnifiedButterflyUnit.scala 43:24]
    end else begin
      c1_del1 <= c1;
    end
    if (reset) begin // @[src/main/scala/UnifiedButterflyUnit.scala 44:24]
      c1_del2 <= 32'h0; // @[src/main/scala/UnifiedButterflyUnit.scala 44:24]
    end else begin
      c1_del2 <= c1_del1;
    end
    ta <= _GEN_37[15:0]; // @[src/main/scala/UnifiedButterflyUnit.scala 46:{19,19}]
    if (reset) begin // @[src/main/scala/UnifiedButterflyUnit.scala 47:19]
      tb <= 32'h0; // @[src/main/scala/UnifiedButterflyUnit.scala 47:19]
    end else begin
      tb <= _GEN_21;
    end
    c2 <= _GEN_38[15:0]; // @[src/main/scala/UnifiedButterflyUnit.scala 49:{19,19}]
    if (reset) begin // @[src/main/scala/UnifiedButterflyUnit.scala 50:19]
      c3 <= 16'h0; // @[src/main/scala/UnifiedButterflyUnit.scala 50:19]
    end else if (c2 > 16'hd01) begin // @[src/main/scala/UnifiedButterflyUnit.scala 105:23]
      c3 <= _c3_T_1; // @[src/main/scala/UnifiedButterflyUnit.scala 106:10]
    end else begin
      c3 <= c2; // @[src/main/scala/UnifiedButterflyUnit.scala 108:10]
    end
    if (reset) begin // @[src/main/scala/UnifiedButterflyUnit.scala 52:22]
      x_reg <= 16'h0; // @[src/main/scala/UnifiedButterflyUnit.scala 52:22]
    end else if (_T) begin // @[src/main/scala/UnifiedButterflyUnit.scala 114:33]
      x_reg <= reg_del5; // @[src/main/scala/UnifiedButterflyUnit.scala 116:13]
    end else if (_T_12 > 16'hd01) begin // @[src/main/scala/UnifiedButterflyUnit.scala 124:36]
      x_reg <= _x_reg_T_3; // @[src/main/scala/UnifiedButterflyUnit.scala 125:15]
    end else begin
      x_reg <= _T_12; // @[src/main/scala/UnifiedButterflyUnit.scala 127:15]
    end
    if (reset) begin // @[src/main/scala/UnifiedButterflyUnit.scala 53:22]
      y_reg <= 16'h0; // @[src/main/scala/UnifiedButterflyUnit.scala 53:22]
    end else if (_T) begin // @[src/main/scala/UnifiedButterflyUnit.scala 114:33]
      y_reg <= c3; // @[src/main/scala/UnifiedButterflyUnit.scala 115:13]
    end else if (reg_del5 > c3) begin // @[src/main/scala/UnifiedButterflyUnit.scala 118:27]
      y_reg <= _y_reg_T_1; // @[src/main/scala/UnifiedButterflyUnit.scala 119:15]
    end else begin
      y_reg <= _y_reg_T_5; // @[src/main/scala/UnifiedButterflyUnit.scala 121:15]
    end
  end
// Register and memory initialization
`ifdef RANDOMIZE_GARBAGE_ASSIGN
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_INVALID_ASSIGN
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_REG_INIT
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_MEM_INIT
`define RANDOMIZE
`endif
`ifndef RANDOM
`define RANDOM $random
`endif
`ifdef RANDOMIZE_MEM_INIT
  integer initvar;
`endif
`ifndef SYNTHESIS
`ifdef FIRRTL_BEFORE_INITIAL
`FIRRTL_BEFORE_INITIAL
`endif
initial begin
  `ifdef RANDOMIZE
    `ifdef INIT_RANDOM
      `INIT_RANDOM
    `endif
    `ifndef VERILATOR
      `ifdef RANDOMIZE_DELAY
        #`RANDOMIZE_DELAY begin end
      `else
        #0.002 begin end
      `endif
    `endif
`ifdef RANDOMIZE_REG_INIT
  _RAND_0 = {1{`RANDOM}};
  str_del0 = _RAND_0[0:0];
  _RAND_1 = {1{`RANDOM}};
  str_del1 = _RAND_1[0:0];
  _RAND_2 = {1{`RANDOM}};
  str_del2 = _RAND_2[0:0];
  _RAND_3 = {1{`RANDOM}};
  str_del3 = _RAND_3[0:0];
  _RAND_4 = {1{`RANDOM}};
  str_del4 = _RAND_4[0:0];
  _RAND_5 = {1{`RANDOM}};
  str_del5 = _RAND_5[0:0];
  _RAND_6 = {1{`RANDOM}};
  str_del6 = _RAND_6[0:0];
  _RAND_7 = {1{`RANDOM}};
  reg0 = _RAND_7[15:0];
  _RAND_8 = {1{`RANDOM}};
  reg1 = _RAND_8[15:0];
  _RAND_9 = {1{`RANDOM}};
  reg2 = _RAND_9[15:0];
  _RAND_10 = {1{`RANDOM}};
  reg_del1 = _RAND_10[15:0];
  _RAND_11 = {1{`RANDOM}};
  reg_del2 = _RAND_11[15:0];
  _RAND_12 = {1{`RANDOM}};
  reg_del3 = _RAND_12[15:0];
  _RAND_13 = {1{`RANDOM}};
  reg_del4 = _RAND_13[15:0];
  _RAND_14 = {1{`RANDOM}};
  reg_del5 = _RAND_14[15:0];
  _RAND_15 = {1{`RANDOM}};
  c1 = _RAND_15[31:0];
  _RAND_16 = {1{`RANDOM}};
  c1_del1 = _RAND_16[31:0];
  _RAND_17 = {1{`RANDOM}};
  c1_del2 = _RAND_17[31:0];
  _RAND_18 = {1{`RANDOM}};
  ta = _RAND_18[15:0];
  _RAND_19 = {1{`RANDOM}};
  tb = _RAND_19[31:0];
  _RAND_20 = {1{`RANDOM}};
  c2 = _RAND_20[15:0];
  _RAND_21 = {1{`RANDOM}};
  c3 = _RAND_21[15:0];
  _RAND_22 = {1{`RANDOM}};
  x_reg = _RAND_22[15:0];
  _RAND_23 = {1{`RANDOM}};
  y_reg = _RAND_23[15:0];
`endif // RANDOMIZE_REG_INIT
  `endif // RANDOMIZE
end // initial
`ifdef FIRRTL_AFTER_INITIAL
`FIRRTL_AFTER_INITIAL
`endif
`endif // SYNTHESIS
endmodule
module TwiddleROM(
  input         io_read_enable, // @[src/main/scala/TwiddleROM.scala 7:14]
  input  [6:0]  io_read_address, // @[src/main/scala/TwiddleROM.scala 7:14]
  output [15:0] io_read_data // @[src/main/scala/TwiddleROM.scala 7:14]
);
  wire [11:0] _GEN_1 = 7'h1 == io_read_address ? 12'h6c1 : 12'h1; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_2 = 7'h2 == io_read_address ? 12'ha14 : _GEN_1; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_3 = 7'h3 == io_read_address ? 12'hcd9 : _GEN_2; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_4 = 7'h4 == io_read_address ? 12'ha52 : _GEN_3; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_5 = 7'h5 == io_read_address ? 12'h276 : _GEN_4; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_6 = 7'h6 == io_read_address ? 12'h769 : _GEN_5; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_7 = 7'h7 == io_read_address ? 12'h350 : _GEN_6; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_8 = 7'h8 == io_read_address ? 12'h426 : _GEN_7; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_9 = 7'h9 == io_read_address ? 12'h77f : _GEN_8; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_10 = 7'ha == io_read_address ? 12'hc1 : _GEN_9; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_11 = 7'hb == io_read_address ? 12'h31d : _GEN_10; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_12 = 7'hc == io_read_address ? 12'hae2 : _GEN_11; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_13 = 7'hd == io_read_address ? 12'hcbc : _GEN_12; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_14 = 7'he == io_read_address ? 12'h239 : _GEN_13; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_15 = 7'hf == io_read_address ? 12'h6d2 : _GEN_14; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_16 = 7'h10 == io_read_address ? 12'h128 : _GEN_15; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_17 = 7'h11 == io_read_address ? 12'h98f : _GEN_16; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_18 = 7'h12 == io_read_address ? 12'h53b : _GEN_17; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_19 = 7'h13 == io_read_address ? 12'h5c4 : _GEN_18; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_20 = 7'h14 == io_read_address ? 12'hbe6 : _GEN_19; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_21 = 7'h15 == io_read_address ? 12'h38 : _GEN_20; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_22 = 7'h16 == io_read_address ? 12'h8c0 : _GEN_21; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_23 = 7'h17 == io_read_address ? 12'h535 : _GEN_22; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_24 = 7'h18 == io_read_address ? 12'h592 : _GEN_23; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_25 = 7'h19 == io_read_address ? 12'h82e : _GEN_24; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_26 = 7'h1a == io_read_address ? 12'h217 : _GEN_25; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_27 = 7'h1b == io_read_address ? 12'hb42 : _GEN_26; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_28 = 7'h1c == io_read_address ? 12'h959 : _GEN_27; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_29 = 7'h1d == io_read_address ? 12'hb3f : _GEN_28; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_30 = 7'h1e == io_read_address ? 12'h7b6 : _GEN_29; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_31 = 7'h1f == io_read_address ? 12'h335 : _GEN_30; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_32 = 7'h20 == io_read_address ? 12'h121 : _GEN_31; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_33 = 7'h21 == io_read_address ? 12'h14b : _GEN_32; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_34 = 7'h22 == io_read_address ? 12'hcb5 : _GEN_33; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_35 = 7'h23 == io_read_address ? 12'h6dc : _GEN_34; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_36 = 7'h24 == io_read_address ? 12'h4ad : _GEN_35; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_37 = 7'h25 == io_read_address ? 12'h900 : _GEN_36; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_38 = 7'h26 == io_read_address ? 12'h8e5 : _GEN_37; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_39 = 7'h27 == io_read_address ? 12'h807 : _GEN_38; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_40 = 7'h28 == io_read_address ? 12'h28a : _GEN_39; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_41 = 7'h29 == io_read_address ? 12'h7b9 : _GEN_40; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_42 = 7'h2a == io_read_address ? 12'h9d1 : _GEN_41; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_43 = 7'h2b == io_read_address ? 12'h278 : _GEN_42; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_44 = 7'h2c == io_read_address ? 12'hb31 : _GEN_43; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_45 = 7'h2d == io_read_address ? 12'h21 : _GEN_44; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_46 = 7'h2e == io_read_address ? 12'h528 : _GEN_45; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_47 = 7'h2f == io_read_address ? 12'h77b : _GEN_46; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_48 = 7'h30 == io_read_address ? 12'h90f : _GEN_47; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_49 = 7'h31 == io_read_address ? 12'h59b : _GEN_48; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_50 = 7'h32 == io_read_address ? 12'h327 : _GEN_49; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_51 = 7'h33 == io_read_address ? 12'h1c4 : _GEN_50; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_52 = 7'h34 == io_read_address ? 12'h59e : _GEN_51; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_53 = 7'h35 == io_read_address ? 12'hb34 : _GEN_52; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_54 = 7'h36 == io_read_address ? 12'h5fe : _GEN_53; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_55 = 7'h37 == io_read_address ? 12'h962 : _GEN_54; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_56 = 7'h38 == io_read_address ? 12'ha57 : _GEN_55; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_57 = 7'h39 == io_read_address ? 12'ha39 : _GEN_56; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_58 = 7'h3a == io_read_address ? 12'h5c9 : _GEN_57; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_59 = 7'h3b == io_read_address ? 12'h288 : _GEN_58; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_60 = 7'h3c == io_read_address ? 12'h9aa : _GEN_59; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_61 = 7'h3d == io_read_address ? 12'hc26 : _GEN_60; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_62 = 7'h3e == io_read_address ? 12'h4cb : _GEN_61; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_63 = 7'h3f == io_read_address ? 12'h38e : _GEN_62; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_64 = 7'h40 == io_read_address ? 12'h11 : _GEN_63; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_65 = 7'h41 == io_read_address ? 12'hac9 : _GEN_64; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_66 = 7'h42 == io_read_address ? 12'h247 : _GEN_65; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_67 = 7'h43 == io_read_address ? 12'ha59 : _GEN_66; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_68 = 7'h44 == io_read_address ? 12'h665 : _GEN_67; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_69 = 7'h45 == io_read_address ? 12'h2d3 : _GEN_68; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_70 = 7'h46 == io_read_address ? 12'h8f0 : _GEN_69; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_71 = 7'h47 == io_read_address ? 12'h44c : _GEN_70; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_72 = 7'h48 == io_read_address ? 12'h581 : _GEN_71; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_73 = 7'h49 == io_read_address ? 12'ha66 : _GEN_72; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_74 = 7'h4a == io_read_address ? 12'hcd1 : _GEN_73; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_75 = 7'h4b == io_read_address ? 12'he9 : _GEN_74; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_76 = 7'h4c == io_read_address ? 12'h2f4 : _GEN_75; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_77 = 7'h4d == io_read_address ? 12'h86c : _GEN_76; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_78 = 7'h4e == io_read_address ? 12'hbc7 : _GEN_77; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_79 = 7'h4f == io_read_address ? 12'hbea : _GEN_78; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_80 = 7'h50 == io_read_address ? 12'h6a7 : _GEN_79; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_81 = 7'h51 == io_read_address ? 12'h673 : _GEN_80; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_82 = 7'h52 == io_read_address ? 12'hae5 : _GEN_81; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_83 = 7'h53 == io_read_address ? 12'h6fd : _GEN_82; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_84 = 7'h54 == io_read_address ? 12'h737 : _GEN_83; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_85 = 7'h55 == io_read_address ? 12'h3b8 : _GEN_84; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_86 = 7'h56 == io_read_address ? 12'h5b5 : _GEN_85; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_87 = 7'h57 == io_read_address ? 12'ha7f : _GEN_86; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_88 = 7'h58 == io_read_address ? 12'h3ab : _GEN_87; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_89 = 7'h59 == io_read_address ? 12'h904 : _GEN_88; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_90 = 7'h5a == io_read_address ? 12'h985 : _GEN_89; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_91 = 7'h5b == io_read_address ? 12'h954 : _GEN_90; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_92 = 7'h5c == io_read_address ? 12'h2dd : _GEN_91; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_93 = 7'h5d == io_read_address ? 12'h921 : _GEN_92; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_94 = 7'h5e == io_read_address ? 12'h10c : _GEN_93; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_95 = 7'h5f == io_read_address ? 12'h281 : _GEN_94; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_96 = 7'h60 == io_read_address ? 12'h630 : _GEN_95; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_97 = 7'h61 == io_read_address ? 12'h8fa : _GEN_96; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_98 = 7'h62 == io_read_address ? 12'h7f5 : _GEN_97; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_99 = 7'h63 == io_read_address ? 12'hc94 : _GEN_98; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_100 = 7'h64 == io_read_address ? 12'h177 : _GEN_99; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_101 = 7'h65 == io_read_address ? 12'h9f5 : _GEN_100; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_102 = 7'h66 == io_read_address ? 12'h82a : _GEN_101; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_103 = 7'h67 == io_read_address ? 12'h66d : _GEN_102; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_104 = 7'h68 == io_read_address ? 12'h427 : _GEN_103; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_105 = 7'h69 == io_read_address ? 12'h13f : _GEN_104; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_106 = 7'h6a == io_read_address ? 12'had5 : _GEN_105; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_107 = 7'h6b == io_read_address ? 12'h2f5 : _GEN_106; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_108 = 7'h6c == io_read_address ? 12'h833 : _GEN_107; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_109 = 7'h6d == io_read_address ? 12'h231 : _GEN_108; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_110 = 7'h6e == io_read_address ? 12'h9a2 : _GEN_109; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_111 = 7'h6f == io_read_address ? 12'ha22 : _GEN_110; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_112 = 7'h70 == io_read_address ? 12'haf4 : _GEN_111; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_113 = 7'h71 == io_read_address ? 12'h444 : _GEN_112; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_114 = 7'h72 == io_read_address ? 12'h193 : _GEN_113; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_115 = 7'h73 == io_read_address ? 12'h402 : _GEN_114; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_116 = 7'h74 == io_read_address ? 12'h477 : _GEN_115; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_117 = 7'h75 == io_read_address ? 12'h866 : _GEN_116; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_118 = 7'h76 == io_read_address ? 12'had7 : _GEN_117; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_119 = 7'h77 == io_read_address ? 12'h376 : _GEN_118; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_120 = 7'h78 == io_read_address ? 12'h6ba : _GEN_119; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_121 = 7'h79 == io_read_address ? 12'h4bc : _GEN_120; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_122 = 7'h7a == io_read_address ? 12'h752 : _GEN_121; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_123 = 7'h7b == io_read_address ? 12'h405 : _GEN_122; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_124 = 7'h7c == io_read_address ? 12'h83e : _GEN_123; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_125 = 7'h7d == io_read_address ? 12'hb77 : _GEN_124; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_126 = 7'h7e == io_read_address ? 12'h375 : _GEN_125; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_127 = 7'h7f == io_read_address ? 12'h86a : _GEN_126; // @[src/main/scala/TwiddleROM.scala 34:{22,22}]
  wire [11:0] _io_read_data_T = io_read_enable ? _GEN_127 : 12'h0; // @[src/main/scala/TwiddleROM.scala 34:22]
  assign io_read_data = {{4'd0}, _io_read_data_T}; // @[src/main/scala/TwiddleROM.scala 34:16]
endmodule
module InverseTwiddleROM(
  input         io_read_enable, // @[src/main/scala/InverseTwiddleROM.scala 7:14]
  input  [6:0]  io_read_address, // @[src/main/scala/InverseTwiddleROM.scala 7:14]
  output [15:0] io_read_data // @[src/main/scala/InverseTwiddleROM.scala 7:14]
);
  wire [11:0] _GEN_1 = 7'h1 == io_read_address ? 12'h640 : 12'h1; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_2 = 7'h2 == io_read_address ? 12'h28 : _GEN_1; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_3 = 7'h3 == io_read_address ? 12'h2ed : _GEN_2; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_4 = 7'h4 == io_read_address ? 12'h9b1 : _GEN_3; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_5 = 7'h5 == io_read_address ? 12'h598 : _GEN_4; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_6 = 7'h6 == io_read_address ? 12'ha8b : _GEN_5; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_7 = 7'h7 == io_read_address ? 12'h2af : _GEN_6; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_8 = 7'h8 == io_read_address ? 12'h62f : _GEN_7; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_9 = 7'h9 == io_read_address ? 12'hac8 : _GEN_8; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_10 = 7'ha == io_read_address ? 12'h45 : _GEN_9; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_11 = 7'hb == io_read_address ? 12'h21f : _GEN_10; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_12 = 7'hc == io_read_address ? 12'h9e4 : _GEN_11; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_13 = 7'hd == io_read_address ? 12'hc40 : _GEN_12; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_14 = 7'he == io_read_address ? 12'h582 : _GEN_13; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_15 = 7'hf == io_read_address ? 12'h8db : _GEN_14; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_16 = 7'h10 == io_read_address ? 12'h9cc : _GEN_15; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_17 = 7'h11 == io_read_address ? 12'h54b : _GEN_16; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_18 = 7'h12 == io_read_address ? 12'h1c2 : _GEN_17; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_19 = 7'h13 == io_read_address ? 12'h3a8 : _GEN_18; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_20 = 7'h14 == io_read_address ? 12'h1bf : _GEN_19; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_21 = 7'h15 == io_read_address ? 12'haea : _GEN_20; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_22 = 7'h16 == io_read_address ? 12'h4d3 : _GEN_21; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_23 = 7'h17 == io_read_address ? 12'h76f : _GEN_22; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_24 = 7'h18 == io_read_address ? 12'h7cc : _GEN_23; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_25 = 7'h19 == io_read_address ? 12'h441 : _GEN_24; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_26 = 7'h1a == io_read_address ? 12'hcc9 : _GEN_25; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_27 = 7'h1b == io_read_address ? 12'h11b : _GEN_26; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_28 = 7'h1c == io_read_address ? 12'h73d : _GEN_27; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_29 = 7'h1d == io_read_address ? 12'h7c6 : _GEN_28; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_30 = 7'h1e == io_read_address ? 12'h372 : _GEN_29; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_31 = 7'h1f == io_read_address ? 12'hbd9 : _GEN_30; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_32 = 7'h20 == io_read_address ? 12'h973 : _GEN_31; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_33 = 7'h21 == io_read_address ? 12'h836 : _GEN_32; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_34 = 7'h22 == io_read_address ? 12'hdb : _GEN_33; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_35 = 7'h23 == io_read_address ? 12'h357 : _GEN_34; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_36 = 7'h24 == io_read_address ? 12'ha79 : _GEN_35; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_37 = 7'h25 == io_read_address ? 12'h738 : _GEN_36; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_38 = 7'h26 == io_read_address ? 12'h2c8 : _GEN_37; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_39 = 7'h27 == io_read_address ? 12'h2aa : _GEN_38; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_40 = 7'h28 == io_read_address ? 12'h39f : _GEN_39; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_41 = 7'h29 == io_read_address ? 12'h703 : _GEN_40; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_42 = 7'h2a == io_read_address ? 12'h1cd : _GEN_41; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_43 = 7'h2b == io_read_address ? 12'h763 : _GEN_42; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_44 = 7'h2c == io_read_address ? 12'hb3d : _GEN_43; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_45 = 7'h2d == io_read_address ? 12'h9da : _GEN_44; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_46 = 7'h2e == io_read_address ? 12'h766 : _GEN_45; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_47 = 7'h2f == io_read_address ? 12'h3f2 : _GEN_46; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_48 = 7'h30 == io_read_address ? 12'h586 : _GEN_47; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_49 = 7'h31 == io_read_address ? 12'h7d9 : _GEN_48; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_50 = 7'h32 == io_read_address ? 12'hce0 : _GEN_49; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_51 = 7'h33 == io_read_address ? 12'h1d0 : _GEN_50; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_52 = 7'h34 == io_read_address ? 12'ha89 : _GEN_51; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_53 = 7'h35 == io_read_address ? 12'h330 : _GEN_52; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_54 = 7'h36 == io_read_address ? 12'h548 : _GEN_53; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_55 = 7'h37 == io_read_address ? 12'ha77 : _GEN_54; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_56 = 7'h38 == io_read_address ? 12'h4fa : _GEN_55; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_57 = 7'h39 == io_read_address ? 12'h41c : _GEN_56; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_58 = 7'h3a == io_read_address ? 12'h401 : _GEN_57; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_59 = 7'h3b == io_read_address ? 12'h854 : _GEN_58; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_60 = 7'h3c == io_read_address ? 12'h625 : _GEN_59; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_61 = 7'h3d == io_read_address ? 12'h4c : _GEN_60; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_62 = 7'h3e == io_read_address ? 12'hbb6 : _GEN_61; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_63 = 7'h3f == io_read_address ? 12'hbe0 : _GEN_62; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_64 = 7'h40 == io_read_address ? 12'h497 : _GEN_63; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_65 = 7'h41 == io_read_address ? 12'h98c : _GEN_64; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_66 = 7'h42 == io_read_address ? 12'h18a : _GEN_65; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_67 = 7'h43 == io_read_address ? 12'h4c3 : _GEN_66; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_68 = 7'h44 == io_read_address ? 12'h8fc : _GEN_67; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_69 = 7'h45 == io_read_address ? 12'h5af : _GEN_68; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_70 = 7'h46 == io_read_address ? 12'h845 : _GEN_69; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_71 = 7'h47 == io_read_address ? 12'h647 : _GEN_70; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_72 = 7'h48 == io_read_address ? 12'h98b : _GEN_71; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_73 = 7'h49 == io_read_address ? 12'h22a : _GEN_72; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_74 = 7'h4a == io_read_address ? 12'h49b : _GEN_73; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_75 = 7'h4b == io_read_address ? 12'h88a : _GEN_74; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_76 = 7'h4c == io_read_address ? 12'h8ff : _GEN_75; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_77 = 7'h4d == io_read_address ? 12'hb6e : _GEN_76; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_78 = 7'h4e == io_read_address ? 12'h8bd : _GEN_77; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_79 = 7'h4f == io_read_address ? 12'h20d : _GEN_78; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_80 = 7'h50 == io_read_address ? 12'h2df : _GEN_79; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_81 = 7'h51 == io_read_address ? 12'h35f : _GEN_80; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_82 = 7'h52 == io_read_address ? 12'had0 : _GEN_81; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_83 = 7'h53 == io_read_address ? 12'h4ce : _GEN_82; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_84 = 7'h54 == io_read_address ? 12'ha0c : _GEN_83; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_85 = 7'h55 == io_read_address ? 12'h22c : _GEN_84; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_86 = 7'h56 == io_read_address ? 12'hbc2 : _GEN_85; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_87 = 7'h57 == io_read_address ? 12'h8da : _GEN_86; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_88 = 7'h58 == io_read_address ? 12'h694 : _GEN_87; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_89 = 7'h59 == io_read_address ? 12'h4d7 : _GEN_88; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_90 = 7'h5a == io_read_address ? 12'h30c : _GEN_89; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_91 = 7'h5b == io_read_address ? 12'hb8a : _GEN_90; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_92 = 7'h5c == io_read_address ? 12'h6d : _GEN_91; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_93 = 7'h5d == io_read_address ? 12'h50c : _GEN_92; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_94 = 7'h5e == io_read_address ? 12'h407 : _GEN_93; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_95 = 7'h5f == io_read_address ? 12'h6d1 : _GEN_94; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_96 = 7'h60 == io_read_address ? 12'ha80 : _GEN_95; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_97 = 7'h61 == io_read_address ? 12'hbf5 : _GEN_96; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_98 = 7'h62 == io_read_address ? 12'h3e0 : _GEN_97; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_99 = 7'h63 == io_read_address ? 12'ha24 : _GEN_98; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_100 = 7'h64 == io_read_address ? 12'h3ad : _GEN_99; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_101 = 7'h65 == io_read_address ? 12'h37c : _GEN_100; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_102 = 7'h66 == io_read_address ? 12'h3fd : _GEN_101; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_103 = 7'h67 == io_read_address ? 12'h956 : _GEN_102; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_104 = 7'h68 == io_read_address ? 12'h282 : _GEN_103; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_105 = 7'h69 == io_read_address ? 12'h74c : _GEN_104; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_106 = 7'h6a == io_read_address ? 12'h949 : _GEN_105; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_107 = 7'h6b == io_read_address ? 12'h5ca : _GEN_106; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_108 = 7'h6c == io_read_address ? 12'h604 : _GEN_107; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_109 = 7'h6d == io_read_address ? 12'h21c : _GEN_108; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_110 = 7'h6e == io_read_address ? 12'h68e : _GEN_109; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_111 = 7'h6f == io_read_address ? 12'h65a : _GEN_110; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_112 = 7'h70 == io_read_address ? 12'h117 : _GEN_111; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_113 = 7'h71 == io_read_address ? 12'h13a : _GEN_112; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_114 = 7'h72 == io_read_address ? 12'h495 : _GEN_113; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_115 = 7'h73 == io_read_address ? 12'ha0d : _GEN_114; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_116 = 7'h74 == io_read_address ? 12'hc18 : _GEN_115; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_117 = 7'h75 == io_read_address ? 12'h30 : _GEN_116; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_118 = 7'h76 == io_read_address ? 12'h29b : _GEN_117; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_119 = 7'h77 == io_read_address ? 12'h780 : _GEN_118; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_120 = 7'h78 == io_read_address ? 12'h8b5 : _GEN_119; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_121 = 7'h79 == io_read_address ? 12'h411 : _GEN_120; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_122 = 7'h7a == io_read_address ? 12'ha2e : _GEN_121; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_123 = 7'h7b == io_read_address ? 12'h69c : _GEN_122; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_124 = 7'h7c == io_read_address ? 12'h2a8 : _GEN_123; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_125 = 7'h7d == io_read_address ? 12'haba : _GEN_124; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_126 = 7'h7e == io_read_address ? 12'h238 : _GEN_125; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _GEN_127 = 7'h7f == io_read_address ? 12'hcf0 : _GEN_126; // @[src/main/scala/InverseTwiddleROM.scala 34:{22,22}]
  wire [11:0] _io_read_data_T = io_read_enable ? _GEN_127 : 12'h0; // @[src/main/scala/InverseTwiddleROM.scala 34:22]
  assign io_read_data = {{4'd0}, _io_read_data_T}; // @[src/main/scala/InverseTwiddleROM.scala 34:16]
endmodule
module DataRAM(
  input         clock,
  input         io_writeValid1, // @[src/main/scala/DataRAM.scala 7:14]
  input  [6:0]  io_writeAddress1, // @[src/main/scala/DataRAM.scala 7:14]
  input  [15:0] io_writeData1, // @[src/main/scala/DataRAM.scala 7:14]
  input         io_writeValid2, // @[src/main/scala/DataRAM.scala 7:14]
  input  [15:0] io_writeData2, // @[src/main/scala/DataRAM.scala 7:14]
  input         io_readValid1, // @[src/main/scala/DataRAM.scala 7:14]
  input  [6:0]  io_readAddress1, // @[src/main/scala/DataRAM.scala 7:14]
  output [15:0] io_readData1, // @[src/main/scala/DataRAM.scala 7:14]
  input         io_readValid2, // @[src/main/scala/DataRAM.scala 7:14]
  input  [6:0]  io_readAddress2, // @[src/main/scala/DataRAM.scala 7:14]
  output [15:0] io_readData2 // @[src/main/scala/DataRAM.scala 7:14]
);
`ifdef RANDOMIZE_MEM_INIT
  reg [31:0] _RAND_0;
`endif // RANDOMIZE_MEM_INIT
`ifdef RANDOMIZE_REG_INIT
  reg [31:0] _RAND_1;
  reg [31:0] _RAND_2;
  reg [31:0] _RAND_3;
  reg [31:0] _RAND_4;
`endif // RANDOMIZE_REG_INIT
  reg [15:0] mem [0:127]; // @[src/main/scala/DataRAM.scala 26:24]
  wire  mem_io_readData1_MPORT_en; // @[src/main/scala/DataRAM.scala 26:24]
  wire [6:0] mem_io_readData1_MPORT_addr; // @[src/main/scala/DataRAM.scala 26:24]
  wire [15:0] mem_io_readData1_MPORT_data; // @[src/main/scala/DataRAM.scala 26:24]
  wire  mem_io_readData2_MPORT_en; // @[src/main/scala/DataRAM.scala 26:24]
  wire [6:0] mem_io_readData2_MPORT_addr; // @[src/main/scala/DataRAM.scala 26:24]
  wire [15:0] mem_io_readData2_MPORT_data; // @[src/main/scala/DataRAM.scala 26:24]
  wire [15:0] mem_MPORT_data; // @[src/main/scala/DataRAM.scala 26:24]
  wire [6:0] mem_MPORT_addr; // @[src/main/scala/DataRAM.scala 26:24]
  wire  mem_MPORT_mask; // @[src/main/scala/DataRAM.scala 26:24]
  wire  mem_MPORT_en; // @[src/main/scala/DataRAM.scala 26:24]
  wire [15:0] mem_MPORT_1_data; // @[src/main/scala/DataRAM.scala 26:24]
  wire [6:0] mem_MPORT_1_addr; // @[src/main/scala/DataRAM.scala 26:24]
  wire  mem_MPORT_1_mask; // @[src/main/scala/DataRAM.scala 26:24]
  wire  mem_MPORT_1_en; // @[src/main/scala/DataRAM.scala 26:24]
  reg  mem_io_readData1_MPORT_en_pipe_0;
  reg [6:0] mem_io_readData1_MPORT_addr_pipe_0;
  reg  mem_io_readData2_MPORT_en_pipe_0;
  reg [6:0] mem_io_readData2_MPORT_addr_pipe_0;
  assign mem_io_readData1_MPORT_en = mem_io_readData1_MPORT_en_pipe_0;
  assign mem_io_readData1_MPORT_addr = mem_io_readData1_MPORT_addr_pipe_0;
  assign mem_io_readData1_MPORT_data = mem[mem_io_readData1_MPORT_addr]; // @[src/main/scala/DataRAM.scala 26:24]
  assign mem_io_readData2_MPORT_en = mem_io_readData2_MPORT_en_pipe_0;
  assign mem_io_readData2_MPORT_addr = mem_io_readData2_MPORT_addr_pipe_0;
  assign mem_io_readData2_MPORT_data = mem[mem_io_readData2_MPORT_addr]; // @[src/main/scala/DataRAM.scala 26:24]
  assign mem_MPORT_data = io_writeData1;
  assign mem_MPORT_addr = io_writeAddress1;
  assign mem_MPORT_mask = 1'h1;
  assign mem_MPORT_en = io_writeValid1;
  assign mem_MPORT_1_data = io_writeData2;
  assign mem_MPORT_1_addr = 7'h0;
  assign mem_MPORT_1_mask = 1'h1;
  assign mem_MPORT_1_en = io_writeValid2;
  assign io_readData1 = io_readValid1 ? mem_io_readData1_MPORT_data : 16'h0; // @[src/main/scala/DataRAM.scala 38:22]
  assign io_readData2 = io_readValid2 ? mem_io_readData2_MPORT_data : 16'h0; // @[src/main/scala/DataRAM.scala 39:22]
  always @(posedge clock) begin
    if (mem_MPORT_en & mem_MPORT_mask) begin
      mem[mem_MPORT_addr] <= mem_MPORT_data; // @[src/main/scala/DataRAM.scala 26:24]
    end
    if (mem_MPORT_1_en & mem_MPORT_1_mask) begin
      mem[mem_MPORT_1_addr] <= mem_MPORT_1_data; // @[src/main/scala/DataRAM.scala 26:24]
    end
    mem_io_readData1_MPORT_en_pipe_0 <= 1'h1;
    if (1'h1) begin
      mem_io_readData1_MPORT_addr_pipe_0 <= io_readAddress1;
    end
    mem_io_readData2_MPORT_en_pipe_0 <= 1'h1;
    if (1'h1) begin
      mem_io_readData2_MPORT_addr_pipe_0 <= io_readAddress2;
    end
  end
// Register and memory initialization
`ifdef RANDOMIZE_GARBAGE_ASSIGN
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_INVALID_ASSIGN
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_REG_INIT
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_MEM_INIT
`define RANDOMIZE
`endif
`ifndef RANDOM
`define RANDOM $random
`endif
`ifdef RANDOMIZE_MEM_INIT
  integer initvar;
`endif
`ifndef SYNTHESIS
`ifdef FIRRTL_BEFORE_INITIAL
`FIRRTL_BEFORE_INITIAL
`endif
initial begin
  `ifdef RANDOMIZE
    `ifdef INIT_RANDOM
      `INIT_RANDOM
    `endif
    `ifndef VERILATOR
      `ifdef RANDOMIZE_DELAY
        #`RANDOMIZE_DELAY begin end
      `else
        #0.002 begin end
      `endif
    `endif
`ifdef RANDOMIZE_MEM_INIT
  _RAND_0 = {1{`RANDOM}};
  for (initvar = 0; initvar < 128; initvar = initvar+1)
    mem[initvar] = _RAND_0[15:0];
`endif // RANDOMIZE_MEM_INIT
`ifdef RANDOMIZE_REG_INIT
  _RAND_1 = {1{`RANDOM}};
  mem_io_readData1_MPORT_en_pipe_0 = _RAND_1[0:0];
  _RAND_2 = {1{`RANDOM}};
  mem_io_readData1_MPORT_addr_pipe_0 = _RAND_2[6:0];
  _RAND_3 = {1{`RANDOM}};
  mem_io_readData2_MPORT_en_pipe_0 = _RAND_3[0:0];
  _RAND_4 = {1{`RANDOM}};
  mem_io_readData2_MPORT_addr_pipe_0 = _RAND_4[6:0];
`endif // RANDOMIZE_REG_INIT
  `endif // RANDOMIZE
end // initial
`ifdef FIRRTL_AFTER_INITIAL
`FIRRTL_AFTER_INITIAL
`endif
`endif // SYNTHESIS
endmodule
module AddressGenerator(
  input        clock,
  input        reset,
  input        io_enable, // @[src/main/scala/AddressGenerator.scala 7:14]
  input        io_select, // @[src/main/scala/AddressGenerator.scala 7:14]
  output [6:0] io_addr1, // @[src/main/scala/AddressGenerator.scala 7:14]
  output [6:0] io_addr2, // @[src/main/scala/AddressGenerator.scala 7:14]
  output [6:0] io_addr3, // @[src/main/scala/AddressGenerator.scala 7:14]
  output       io_inverse, // @[src/main/scala/AddressGenerator.scala 7:14]
  output       io_finish // @[src/main/scala/AddressGenerator.scala 7:14]
);
`ifdef RANDOMIZE_REG_INIT
  reg [31:0] _RAND_0;
  reg [31:0] _RAND_1;
  reg [31:0] _RAND_2;
  reg [31:0] _RAND_3;
  reg [31:0] _RAND_4;
`endif // RANDOMIZE_REG_INIT
  reg [3:0] fin_count; // @[src/main/scala/AddressGenerator.scala 37:26]
  reg [6:0] counter; // @[src/main/scala/AddressGenerator.scala 38:24]
  reg [2:0] state; // @[src/main/scala/AddressGenerator.scala 39:24]
  reg  inverse; // @[src/main/scala/AddressGenerator.scala 40:25]
  reg  str_reg; // @[src/main/scala/AddressGenerator.scala 41:24]
  wire  _GEN_0 = io_enable | str_reg; // @[src/main/scala/AddressGenerator.scala 57:19 58:13 41:24]
  wire  _GEN_4 = state == 3'h6 & io_select; // @[src/main/scala/AddressGenerator.scala 71:27 81:17]
  wire  _T_5 = state == 3'h7; // @[src/main/scala/AddressGenerator.scala 83:43]
  wire  _T_6 = counter == 7'h7f & state == 3'h7; // @[src/main/scala/AddressGenerator.scala 83:34]
  wire  _GEN_9 = counter == 7'h3f & state != 3'h7 ? _GEN_4 : _T_6; // @[src/main/scala/AddressGenerator.scala 69:45]
  wire  fin_sig = str_reg & _GEN_9; // @[src/main/scala/AddressGenerator.scala 65:17 91:13]
  wire [6:0] _counter_T_1 = counter + 7'h1; // @[src/main/scala/AddressGenerator.scala 67:24]
  wire [2:0] _state_T_1 = state + 3'h1; // @[src/main/scala/AddressGenerator.scala 77:26]
  wire [2:0] _GEN_3 = io_select ? 3'h0 : _state_T_1; // @[src/main/scala/AddressGenerator.scala 72:25 74:17 77:17]
  wire [2:0] _i_T_1 = 3'h6 - state; // @[src/main/scala/AddressGenerator.scala 94:38]
  wire [2:0] i = io_select ? state : _i_T_1; // @[src/main/scala/AddressGenerator.scala 94:14]
  wire [6:0] s = _T_5 ? 7'h0 : counter; // @[src/main/scala/AddressGenerator.scala 96:14]
  wire [2:0] _j_T_2 = 3'h6 - i; // @[src/main/scala/AddressGenerator.scala 97:51]
  wire [6:0] _j_T_3 = s >> _j_T_2; // @[src/main/scala/AddressGenerator.scala 97:43]
  wire [6:0] j = _T_5 ? 7'h0 : _j_T_3; // @[src/main/scala/AddressGenerator.scala 97:14]
  wire [6:0] _k_T_1 = 7'h40 >> i; // @[src/main/scala/AddressGenerator.scala 98:52]
  wire [6:0] _k_T_3 = _k_T_1 - 7'h1; // @[src/main/scala/AddressGenerator.scala 98:58]
  wire [6:0] _k_T_4 = s & _k_T_3; // @[src/main/scala/AddressGenerator.scala 98:43]
  wire [6:0] k = _T_5 ? 7'h0 : _k_T_4; // @[src/main/scala/AddressGenerator.scala 98:14]
  wire [2:0] _u7mi_T_2 = 3'h7 - i; // @[src/main/scala/AddressGenerator.scala 101:48]
  wire [2:0] u7mi = _T_5 ? 3'h0 : _u7mi_T_2; // @[src/main/scala/AddressGenerator.scala 101:17]
  wire [2:0] u6mi = _T_5 ? 3'h0 : _j_T_2; // @[src/main/scala/AddressGenerator.scala 102:17]
  wire [7:0] _u7s1_T_1 = 8'h1 << u7mi; // @[src/main/scala/AddressGenerator.scala 103:48]
  wire [7:0] u7s1 = _T_5 ? 8'h0 : _u7s1_T_1; // @[src/main/scala/AddressGenerator.scala 103:17]
  wire [7:0] _u6s1_T_1 = 8'h1 << u6mi; // @[src/main/scala/AddressGenerator.scala 104:48]
  wire [7:0] u6s1 = _T_5 ? 8'h0 : _u6s1_T_1; // @[src/main/scala/AddressGenerator.scala 104:17]
  wire [7:0] _usi_T_1 = 8'h1 << i; // @[src/main/scala/AddressGenerator.scala 105:48]
  wire [7:0] usi = _T_5 ? 8'h0 : _usi_T_1; // @[src/main/scala/AddressGenerator.scala 105:17]
  wire [6:0] _u7ss_T_1 = s >> u6mi; // @[src/main/scala/AddressGenerator.scala 106:46]
  wire [6:0] u7ss = _T_5 ? 7'h0 : _u7ss_T_1; // @[src/main/scala/AddressGenerator.scala 106:17]
  wire [14:0] _addr1_var_T_1 = j * u7s1; // @[src/main/scala/AddressGenerator.scala 108:51]
  wire [14:0] _GEN_30 = {{8'd0}, k}; // @[src/main/scala/AddressGenerator.scala 108:58]
  wire [14:0] _addr1_var_T_3 = _addr1_var_T_1 + _GEN_30; // @[src/main/scala/AddressGenerator.scala 108:58]
  wire [14:0] addr1_var = _T_5 ? 15'h0 : _addr1_var_T_3; // @[src/main/scala/AddressGenerator.scala 108:22]
  wire [14:0] _GEN_32 = {{7'd0}, u6s1}; // @[src/main/scala/AddressGenerator.scala 109:61]
  wire [14:0] _addr2_var_T_5 = _addr1_var_T_3 + _GEN_32; // @[src/main/scala/AddressGenerator.scala 109:61]
  wire [14:0] addr2_var = _T_5 ? {{8'd0}, counter} : _addr2_var_T_5; // @[src/main/scala/AddressGenerator.scala 109:22]
  wire [7:0] _GEN_33 = {{1'd0}, u7ss}; // @[src/main/scala/AddressGenerator.scala 110:53]
  wire [7:0] _addr3_var_T_2 = usi + _GEN_33; // @[src/main/scala/AddressGenerator.scala 110:53]
  wire [7:0] addr3_var = _T_5 ? 8'h0 : _addr3_var_T_2; // @[src/main/scala/AddressGenerator.scala 110:22]
  wire  _T_14 = fin_count > 4'h0; // @[src/main/scala/AddressGenerator.scala 140:29]
  wire [3:0] _fin_count_T_1 = fin_count + 4'h1; // @[src/main/scala/AddressGenerator.scala 141:28]
  assign io_addr1 = addr1_var[6:0]; // @[src/main/scala/AddressGenerator.scala 145:14]
  assign io_addr2 = addr2_var[6:0]; // @[src/main/scala/AddressGenerator.scala 146:14]
  assign io_addr3 = addr3_var[6:0]; // @[src/main/scala/AddressGenerator.scala 147:14]
  assign io_inverse = inverse | ~io_select & (_T_14 & fin_count <= 4'h8); // @[src/main/scala/AddressGenerator.scala 151:26]
  assign io_finish = fin_count == 4'h8; // @[src/main/scala/AddressGenerator.scala 150:28]
  always @(posedge clock) begin
    if (reset) begin // @[src/main/scala/AddressGenerator.scala 37:26]
      fin_count <= 4'h0; // @[src/main/scala/AddressGenerator.scala 37:26]
    end else if (fin_sig | fin_count > 4'h0) begin // @[src/main/scala/AddressGenerator.scala 140:36]
      fin_count <= _fin_count_T_1; // @[src/main/scala/AddressGenerator.scala 141:15]
    end
    if (reset) begin // @[src/main/scala/AddressGenerator.scala 38:24]
      counter <= 7'h0; // @[src/main/scala/AddressGenerator.scala 38:24]
    end else if (str_reg) begin // @[src/main/scala/AddressGenerator.scala 65:17]
      if (counter == 7'h3f & state != 3'h7) begin // @[src/main/scala/AddressGenerator.scala 69:45]
        counter <= 7'h0; // @[src/main/scala/AddressGenerator.scala 70:15]
      end else begin
        counter <= _counter_T_1; // @[src/main/scala/AddressGenerator.scala 67:13]
      end
    end
    if (reset) begin // @[src/main/scala/AddressGenerator.scala 39:24]
      state <= 3'h0; // @[src/main/scala/AddressGenerator.scala 39:24]
    end else if (str_reg) begin // @[src/main/scala/AddressGenerator.scala 65:17]
      if (counter == 7'h3f & state != 3'h7) begin // @[src/main/scala/AddressGenerator.scala 69:45]
        if (state == 3'h6) begin // @[src/main/scala/AddressGenerator.scala 71:27]
          state <= _GEN_3;
        end else begin
          state <= _state_T_1; // @[src/main/scala/AddressGenerator.scala 80:15]
        end
      end else if (counter == 7'h7f & state == 3'h7) begin // @[src/main/scala/AddressGenerator.scala 83:52]
        state <= 3'h0; // @[src/main/scala/AddressGenerator.scala 85:13]
      end
    end
    if (reset) begin // @[src/main/scala/AddressGenerator.scala 40:25]
      inverse <= 1'h0; // @[src/main/scala/AddressGenerator.scala 40:25]
    end else begin
      inverse <= _T_5; // @[src/main/scala/AddressGenerator.scala 111:11]
    end
    if (reset) begin // @[src/main/scala/AddressGenerator.scala 41:24]
      str_reg <= 1'h0; // @[src/main/scala/AddressGenerator.scala 41:24]
    end else if (fin_sig) begin // @[src/main/scala/AddressGenerator.scala 61:17]
      str_reg <= 1'h0; // @[src/main/scala/AddressGenerator.scala 62:13]
    end else begin
      str_reg <= _GEN_0;
    end
  end
// Register and memory initialization
`ifdef RANDOMIZE_GARBAGE_ASSIGN
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_INVALID_ASSIGN
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_REG_INIT
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_MEM_INIT
`define RANDOMIZE
`endif
`ifndef RANDOM
`define RANDOM $random
`endif
`ifdef RANDOMIZE_MEM_INIT
  integer initvar;
`endif
`ifndef SYNTHESIS
`ifdef FIRRTL_BEFORE_INITIAL
`FIRRTL_BEFORE_INITIAL
`endif
initial begin
  `ifdef RANDOMIZE
    `ifdef INIT_RANDOM
      `INIT_RANDOM
    `endif
    `ifndef VERILATOR
      `ifdef RANDOMIZE_DELAY
        #`RANDOMIZE_DELAY begin end
      `else
        #0.002 begin end
      `endif
    `endif
`ifdef RANDOMIZE_REG_INIT
  _RAND_0 = {1{`RANDOM}};
  fin_count = _RAND_0[3:0];
  _RAND_1 = {1{`RANDOM}};
  counter = _RAND_1[6:0];
  _RAND_2 = {1{`RANDOM}};
  state = _RAND_2[2:0];
  _RAND_3 = {1{`RANDOM}};
  inverse = _RAND_3[0:0];
  _RAND_4 = {1{`RANDOM}};
  str_reg = _RAND_4[0:0];
`endif // RANDOMIZE_REG_INIT
  `endif // RANDOMIZE
end // initial
`ifdef FIRRTL_AFTER_INITIAL
`FIRRTL_AFTER_INITIAL
`endif
`endif // SYNTHESIS
endmodule
module NTT(
  input         clock,
  input         reset,
  input  [2:0]  io_regWrAddr, // @[src/main/scala/NTT.scala 7:14]
  input  [31:0] io_regWrData, // @[src/main/scala/NTT.scala 7:14]
  input         io_regWriteEn, // @[src/main/scala/NTT.scala 7:14]
  input  [2:0]  io_regReadAddr, // @[src/main/scala/NTT.scala 7:14]
  output [31:0] io_regReadData // @[src/main/scala/NTT.scala 7:14]
);
`ifdef RANDOMIZE_REG_INIT
  reg [31:0] _RAND_0;
  reg [31:0] _RAND_1;
  reg [31:0] _RAND_2;
  reg [31:0] _RAND_3;
  reg [31:0] _RAND_4;
  reg [31:0] _RAND_5;
  reg [31:0] _RAND_6;
  reg [31:0] _RAND_7;
  reg [31:0] _RAND_8;
  reg [31:0] _RAND_9;
  reg [31:0] _RAND_10;
`endif // RANDOMIZE_REG_INIT
  wire  bf_unit1_clock; // @[src/main/scala/NTT.scala 33:24]
  wire  bf_unit1_reset; // @[src/main/scala/NTT.scala 33:24]
  wire  bf_unit1_io_start; // @[src/main/scala/NTT.scala 33:24]
  wire  bf_unit1_io_select; // @[src/main/scala/NTT.scala 33:24]
  wire  bf_unit1_io_inverse; // @[src/main/scala/NTT.scala 33:24]
  wire [15:0] bf_unit1_io_u; // @[src/main/scala/NTT.scala 33:24]
  wire [15:0] bf_unit1_io_v; // @[src/main/scala/NTT.scala 33:24]
  wire [15:0] bf_unit1_io_w; // @[src/main/scala/NTT.scala 33:24]
  wire [15:0] bf_unit1_io_x; // @[src/main/scala/NTT.scala 33:24]
  wire [15:0] bf_unit1_io_y; // @[src/main/scala/NTT.scala 33:24]
  wire  bf_unit1_io_finish; // @[src/main/scala/NTT.scala 33:24]
  wire  bf_unit2_clock; // @[src/main/scala/NTT.scala 34:24]
  wire  bf_unit2_reset; // @[src/main/scala/NTT.scala 34:24]
  wire  bf_unit2_io_start; // @[src/main/scala/NTT.scala 34:24]
  wire  bf_unit2_io_select; // @[src/main/scala/NTT.scala 34:24]
  wire  bf_unit2_io_inverse; // @[src/main/scala/NTT.scala 34:24]
  wire [15:0] bf_unit2_io_u; // @[src/main/scala/NTT.scala 34:24]
  wire [15:0] bf_unit2_io_v; // @[src/main/scala/NTT.scala 34:24]
  wire [15:0] bf_unit2_io_w; // @[src/main/scala/NTT.scala 34:24]
  wire [15:0] bf_unit2_io_x; // @[src/main/scala/NTT.scala 34:24]
  wire [15:0] bf_unit2_io_y; // @[src/main/scala/NTT.scala 34:24]
  wire  bf_unit2_io_finish; // @[src/main/scala/NTT.scala 34:24]
  wire  tf_rom_io_read_enable; // @[src/main/scala/NTT.scala 35:22]
  wire [6:0] tf_rom_io_read_address; // @[src/main/scala/NTT.scala 35:22]
  wire [15:0] tf_rom_io_read_data; // @[src/main/scala/NTT.scala 35:22]
  wire  itf_rom_io_read_enable; // @[src/main/scala/NTT.scala 36:23]
  wire [6:0] itf_rom_io_read_address; // @[src/main/scala/NTT.scala 36:23]
  wire [15:0] itf_rom_io_read_data; // @[src/main/scala/NTT.scala 36:23]
  wire  data_ram1_clock; // @[src/main/scala/NTT.scala 37:25]
  wire  data_ram1_io_writeValid1; // @[src/main/scala/NTT.scala 37:25]
  wire [6:0] data_ram1_io_writeAddress1; // @[src/main/scala/NTT.scala 37:25]
  wire [15:0] data_ram1_io_writeData1; // @[src/main/scala/NTT.scala 37:25]
  wire  data_ram1_io_writeValid2; // @[src/main/scala/NTT.scala 37:25]
  wire [15:0] data_ram1_io_writeData2; // @[src/main/scala/NTT.scala 37:25]
  wire  data_ram1_io_readValid1; // @[src/main/scala/NTT.scala 37:25]
  wire [6:0] data_ram1_io_readAddress1; // @[src/main/scala/NTT.scala 37:25]
  wire [15:0] data_ram1_io_readData1; // @[src/main/scala/NTT.scala 37:25]
  wire  data_ram1_io_readValid2; // @[src/main/scala/NTT.scala 37:25]
  wire [6:0] data_ram1_io_readAddress2; // @[src/main/scala/NTT.scala 37:25]
  wire [15:0] data_ram1_io_readData2; // @[src/main/scala/NTT.scala 37:25]
  wire  data_ram2_clock; // @[src/main/scala/NTT.scala 38:25]
  wire  data_ram2_io_writeValid1; // @[src/main/scala/NTT.scala 38:25]
  wire [6:0] data_ram2_io_writeAddress1; // @[src/main/scala/NTT.scala 38:25]
  wire [15:0] data_ram2_io_writeData1; // @[src/main/scala/NTT.scala 38:25]
  wire  data_ram2_io_writeValid2; // @[src/main/scala/NTT.scala 38:25]
  wire [15:0] data_ram2_io_writeData2; // @[src/main/scala/NTT.scala 38:25]
  wire  data_ram2_io_readValid1; // @[src/main/scala/NTT.scala 38:25]
  wire [6:0] data_ram2_io_readAddress1; // @[src/main/scala/NTT.scala 38:25]
  wire [15:0] data_ram2_io_readData1; // @[src/main/scala/NTT.scala 38:25]
  wire  data_ram2_io_readValid2; // @[src/main/scala/NTT.scala 38:25]
  wire [6:0] data_ram2_io_readAddress2; // @[src/main/scala/NTT.scala 38:25]
  wire [15:0] data_ram2_io_readData2; // @[src/main/scala/NTT.scala 38:25]
  wire  addr_gen_clock; // @[src/main/scala/NTT.scala 39:24]
  wire  addr_gen_reset; // @[src/main/scala/NTT.scala 39:24]
  wire  addr_gen_io_enable; // @[src/main/scala/NTT.scala 39:24]
  wire  addr_gen_io_select; // @[src/main/scala/NTT.scala 39:24]
  wire [6:0] addr_gen_io_addr1; // @[src/main/scala/NTT.scala 39:24]
  wire [6:0] addr_gen_io_addr2; // @[src/main/scala/NTT.scala 39:24]
  wire [6:0] addr_gen_io_addr3; // @[src/main/scala/NTT.scala 39:24]
  wire  addr_gen_io_inverse; // @[src/main/scala/NTT.scala 39:24]
  wire  addr_gen_io_finish; // @[src/main/scala/NTT.scala 39:24]
  reg [31:0] regs_0; // @[src/main/scala/NTT.scala 22:21]
  reg [31:0] regs_1; // @[src/main/scala/NTT.scala 22:21]
  reg [31:0] regs_2; // @[src/main/scala/NTT.scala 22:21]
  reg [31:0] regs_3; // @[src/main/scala/NTT.scala 22:21]
  reg [31:0] regs_4; // @[src/main/scala/NTT.scala 22:21]
  reg [31:0] regs_5; // @[src/main/scala/NTT.scala 22:21]
  reg [31:0] regs_6; // @[src/main/scala/NTT.scala 22:21]
  wire [31:0] _GEN_1 = 3'h1 == io_regWrAddr ? io_regWrData : regs_1; // @[src/main/scala/NTT.scala 22:21 26:{24,24}]
  wire [31:0] _GEN_2 = 3'h2 == io_regWrAddr ? io_regWrData : regs_2; // @[src/main/scala/NTT.scala 22:21 26:{24,24}]
  wire [31:0] _GEN_3 = 3'h3 == io_regWrAddr ? io_regWrData : regs_3; // @[src/main/scala/NTT.scala 22:21 26:{24,24}]
  wire [31:0] _GEN_9 = io_regWriteEn ? _GEN_2 : regs_2; // @[src/main/scala/NTT.scala 22:21 25:23]
  wire [31:0] _GEN_15 = 3'h1 == io_regReadAddr ? regs_1 : regs_0; // @[src/main/scala/NTT.scala 30:{18,18}]
  wire [31:0] _GEN_16 = 3'h2 == io_regReadAddr ? regs_2 : _GEN_15; // @[src/main/scala/NTT.scala 30:{18,18}]
  wire [31:0] _GEN_17 = 3'h3 == io_regReadAddr ? regs_3 : _GEN_16; // @[src/main/scala/NTT.scala 30:{18,18}]
  wire [31:0] _GEN_18 = 3'h4 == io_regReadAddr ? regs_4 : _GEN_17; // @[src/main/scala/NTT.scala 30:{18,18}]
  wire [31:0] _GEN_19 = 3'h5 == io_regReadAddr ? regs_5 : _GEN_18; // @[src/main/scala/NTT.scala 30:{18,18}]
  reg  start_bf; // @[src/main/scala/NTT.scala 42:25]
  reg  read_done1a; // @[src/main/scala/NTT.scala 43:28]
  reg  read_done1b; // @[src/main/scala/NTT.scala 44:28]
  reg  read_done2; // @[src/main/scala/NTT.scala 45:27]
  wire  _GEN_24 = regs_0 == 32'h1 | start_bf; // @[src/main/scala/NTT.scala 48:25 52:14 42:25]
  wire  _T_2 = regs_2 == 32'h1; // @[src/main/scala/NTT.scala 98:17]
  wire  _T_3 = regs_6 == 32'h1; // @[src/main/scala/NTT.scala 98:38]
  wire  _T_5 = regs_1 == 32'h0; // @[src/main/scala/NTT.scala 103:45]
  wire  _T_6 = bf_unit1_io_finish & regs_1 == 32'h0; // @[src/main/scala/NTT.scala 103:33]
  wire  _T_7 = ~addr_gen_io_inverse; // @[src/main/scala/NTT.scala 103:57]
  wire  _T_8 = bf_unit1_io_finish & regs_1 == 32'h0 & ~addr_gen_io_inverse; // @[src/main/scala/NTT.scala 103:54]
  wire [15:0] _GEN_31 = bf_unit1_io_finish & regs_1 == 32'h0 & ~addr_gen_io_inverse ? bf_unit1_io_x : 16'h0; // @[src/main/scala/NTT.scala 103:79 106:29 110:29]
  wire [31:0] _GEN_33 = regs_2 == 32'h1 & regs_6 == 32'h1 ? regs_4 : 32'h0; // @[src/main/scala/NTT.scala 100:32 98:48]
  wire [31:0] _GEN_34 = regs_2 == 32'h1 & regs_6 == 32'h1 ? regs_3 : {{16'd0}, _GEN_31}; // @[src/main/scala/NTT.scala 101:29 98:48]
  wire  _T_10 = regs_6 == 32'h2; // @[src/main/scala/NTT.scala 114:38]
  wire  _T_13 = bf_unit2_io_finish & _T_5; // @[src/main/scala/NTT.scala 119:33]
  wire  _T_15 = bf_unit2_io_finish & _T_5 & _T_7; // @[src/main/scala/NTT.scala 119:54]
  wire [15:0] _GEN_38 = bf_unit2_io_finish & _T_5 & _T_7 ? bf_unit2_io_x : 16'h0; // @[src/main/scala/NTT.scala 119:79 122:29 126:29]
  wire [31:0] _GEN_40 = _T_2 & regs_6 == 32'h2 ? regs_4 : 32'h0; // @[src/main/scala/NTT.scala 114:48 116:32]
  wire [31:0] _GEN_41 = _T_2 & regs_6 == 32'h2 ? regs_3 : {{16'd0}, _GEN_38}; // @[src/main/scala/NTT.scala 114:48 117:29]
  wire  _T_22 = regs_2 == 32'h2 & regs_1 == 32'h1; // @[src/main/scala/NTT.scala 152:26]
  wire [6:0] _GEN_50 = start_bf ? addr_gen_io_addr1 : 7'h0; // @[src/main/scala/NTT.scala 155:24 157:31 160:31]
  wire [31:0] _GEN_52 = regs_2 == 32'h2 & regs_1 == 32'h1 & _T_3 ? regs_4 : {{25'd0}, _GEN_50}; // @[src/main/scala/NTT.scala 152:69 154:31]
  wire [31:0] _GEN_54 = _T_22 & _T_10 ? regs_4 : {{25'd0}, _GEN_50}; // @[src/main/scala/NTT.scala 164:69 166:31]
  wire [15:0] _GEN_62 = addr_gen_io_inverse ? 16'h0 : data_ram1_io_readData1; // @[src/main/scala/NTT.scala 214:31 215:21 218:21]
  wire [15:0] _GEN_63 = addr_gen_io_inverse ? 16'h0 : data_ram2_io_readData1; // @[src/main/scala/NTT.scala 214:31 216:21 219:21]
  UnifiedButterflyUnit bf_unit1 ( // @[src/main/scala/NTT.scala 33:24]
    .clock(bf_unit1_clock),
    .reset(bf_unit1_reset),
    .io_start(bf_unit1_io_start),
    .io_select(bf_unit1_io_select),
    .io_inverse(bf_unit1_io_inverse),
    .io_u(bf_unit1_io_u),
    .io_v(bf_unit1_io_v),
    .io_w(bf_unit1_io_w),
    .io_x(bf_unit1_io_x),
    .io_y(bf_unit1_io_y),
    .io_finish(bf_unit1_io_finish)
  );
  UnifiedButterflyUnit bf_unit2 ( // @[src/main/scala/NTT.scala 34:24]
    .clock(bf_unit2_clock),
    .reset(bf_unit2_reset),
    .io_start(bf_unit2_io_start),
    .io_select(bf_unit2_io_select),
    .io_inverse(bf_unit2_io_inverse),
    .io_u(bf_unit2_io_u),
    .io_v(bf_unit2_io_v),
    .io_w(bf_unit2_io_w),
    .io_x(bf_unit2_io_x),
    .io_y(bf_unit2_io_y),
    .io_finish(bf_unit2_io_finish)
  );
  TwiddleROM tf_rom ( // @[src/main/scala/NTT.scala 35:22]
    .io_read_enable(tf_rom_io_read_enable),
    .io_read_address(tf_rom_io_read_address),
    .io_read_data(tf_rom_io_read_data)
  );
  InverseTwiddleROM itf_rom ( // @[src/main/scala/NTT.scala 36:23]
    .io_read_enable(itf_rom_io_read_enable),
    .io_read_address(itf_rom_io_read_address),
    .io_read_data(itf_rom_io_read_data)
  );
  DataRAM data_ram1 ( // @[src/main/scala/NTT.scala 37:25]
    .clock(data_ram1_clock),
    .io_writeValid1(data_ram1_io_writeValid1),
    .io_writeAddress1(data_ram1_io_writeAddress1),
    .io_writeData1(data_ram1_io_writeData1),
    .io_writeValid2(data_ram1_io_writeValid2),
    .io_writeData2(data_ram1_io_writeData2),
    .io_readValid1(data_ram1_io_readValid1),
    .io_readAddress1(data_ram1_io_readAddress1),
    .io_readData1(data_ram1_io_readData1),
    .io_readValid2(data_ram1_io_readValid2),
    .io_readAddress2(data_ram1_io_readAddress2),
    .io_readData2(data_ram1_io_readData2)
  );
  DataRAM data_ram2 ( // @[src/main/scala/NTT.scala 38:25]
    .clock(data_ram2_clock),
    .io_writeValid1(data_ram2_io_writeValid1),
    .io_writeAddress1(data_ram2_io_writeAddress1),
    .io_writeData1(data_ram2_io_writeData1),
    .io_writeValid2(data_ram2_io_writeValid2),
    .io_writeData2(data_ram2_io_writeData2),
    .io_readValid1(data_ram2_io_readValid1),
    .io_readAddress1(data_ram2_io_readAddress1),
    .io_readData1(data_ram2_io_readData1),
    .io_readValid2(data_ram2_io_readValid2),
    .io_readAddress2(data_ram2_io_readAddress2),
    .io_readData2(data_ram2_io_readData2)
  );
  AddressGenerator addr_gen ( // @[src/main/scala/NTT.scala 39:24]
    .clock(addr_gen_clock),
    .reset(addr_gen_reset),
    .io_enable(addr_gen_io_enable),
    .io_select(addr_gen_io_select),
    .io_addr1(addr_gen_io_addr1),
    .io_addr2(addr_gen_io_addr2),
    .io_addr3(addr_gen_io_addr3),
    .io_inverse(addr_gen_io_inverse),
    .io_finish(addr_gen_io_finish)
  );
  assign io_regReadData = 3'h6 == io_regReadAddr ? regs_6 : _GEN_19; // @[src/main/scala/NTT.scala 30:{18,18}]
  assign bf_unit1_clock = clock;
  assign bf_unit1_reset = reset;
  assign bf_unit1_io_start = read_done1b | read_done2; // @[src/main/scala/NTT.scala 91:36]
  assign bf_unit1_io_select = regs_5 == 32'h0 ? 1'h0 : 1'h1; // @[src/main/scala/NTT.scala 58:25 59:24 63:24]
  assign bf_unit1_io_inverse = addr_gen_io_inverse; // @[src/main/scala/NTT.scala 92:23]
  assign bf_unit1_io_u = read_done1b ? _GEN_62 : 16'h0; // @[src/main/scala/NTT.scala 213:21 222:19]
  assign bf_unit1_io_v = read_done2 ? data_ram1_io_readData2 : 16'h0; // @[src/main/scala/NTT.scala 227:20 228:19 231:19]
  assign bf_unit1_io_w = addr_gen_io_select ? tf_rom_io_read_data : itf_rom_io_read_data; // @[src/main/scala/NTT.scala 81:28 82:19 85:19]
  assign bf_unit2_clock = clock;
  assign bf_unit2_reset = reset;
  assign bf_unit2_io_start = read_done1b | read_done2; // @[src/main/scala/NTT.scala 94:36]
  assign bf_unit2_io_select = regs_5 == 32'h0 ? 1'h0 : 1'h1; // @[src/main/scala/NTT.scala 58:25 59:24 63:24]
  assign bf_unit2_io_inverse = addr_gen_io_inverse; // @[src/main/scala/NTT.scala 95:23]
  assign bf_unit2_io_u = read_done1b ? _GEN_63 : 16'h0; // @[src/main/scala/NTT.scala 213:21 223:19]
  assign bf_unit2_io_v = read_done2 ? data_ram2_io_readData2 : 16'h0; // @[src/main/scala/NTT.scala 227:20 229:19 232:19]
  assign bf_unit2_io_w = addr_gen_io_select ? tf_rom_io_read_data : itf_rom_io_read_data; // @[src/main/scala/NTT.scala 81:28 82:19 85:19]
  assign tf_rom_io_read_enable = start_bf; // @[src/main/scala/NTT.scala 75:25]
  assign tf_rom_io_read_address = addr_gen_io_addr3; // @[src/main/scala/NTT.scala 76:26]
  assign itf_rom_io_read_enable = start_bf; // @[src/main/scala/NTT.scala 77:26]
  assign itf_rom_io_read_address = addr_gen_io_addr3; // @[src/main/scala/NTT.scala 78:27]
  assign data_ram1_clock = clock;
  assign data_ram1_io_writeValid1 = regs_2 == 32'h1 & regs_6 == 32'h1 | _T_8; // @[src/main/scala/NTT.scala 98:48 99:30]
  assign data_ram1_io_writeAddress1 = _GEN_33[6:0];
  assign data_ram1_io_writeData1 = _GEN_34[15:0];
  assign data_ram1_io_writeValid2 = bf_unit1_io_finish & _T_5; // @[src/main/scala/NTT.scala 130:27]
  assign data_ram1_io_writeData2 = _T_6 ? bf_unit1_io_y : 16'h0; // @[src/main/scala/NTT.scala 130:49 133:29 137:29]
  assign data_ram1_io_readValid1 = regs_2 == 32'h2 & regs_1 == 32'h1 & _T_3 | start_bf; // @[src/main/scala/NTT.scala 152:69 153:29]
  assign data_ram1_io_readAddress1 = _GEN_52[6:0];
  assign data_ram1_io_readValid2 = start_bf; // @[src/main/scala/NTT.scala 155:24 156:29 159:29]
  assign data_ram1_io_readAddress2 = start_bf ? addr_gen_io_addr2 : 7'h0; // @[src/main/scala/NTT.scala 189:18 191:31 197:31]
  assign data_ram2_clock = clock;
  assign data_ram2_io_writeValid1 = _T_2 & regs_6 == 32'h2 | _T_15; // @[src/main/scala/NTT.scala 114:48 115:30]
  assign data_ram2_io_writeAddress1 = _GEN_40[6:0];
  assign data_ram2_io_writeData1 = _GEN_41[15:0];
  assign data_ram2_io_writeValid2 = bf_unit2_io_finish & _T_5; // @[src/main/scala/NTT.scala 141:27]
  assign data_ram2_io_writeData2 = _T_13 ? bf_unit2_io_y : 16'h0; // @[src/main/scala/NTT.scala 141:49 144:29 148:29]
  assign data_ram2_io_readValid1 = _T_22 & _T_10 | start_bf; // @[src/main/scala/NTT.scala 164:69 165:29]
  assign data_ram2_io_readAddress1 = _GEN_54[6:0];
  assign data_ram2_io_readValid2 = start_bf; // @[src/main/scala/NTT.scala 155:24 156:29 159:29]
  assign data_ram2_io_readAddress2 = start_bf ? addr_gen_io_addr2 : 7'h0; // @[src/main/scala/NTT.scala 189:18 191:31 197:31]
  assign addr_gen_clock = clock;
  assign addr_gen_reset = reset;
  assign addr_gen_io_enable = regs_0 == 32'h1; // @[src/main/scala/NTT.scala 48:16]
  assign addr_gen_io_select = regs_5 == 32'h0 ? 1'h0 : 1'h1; // @[src/main/scala/NTT.scala 58:25 59:24 63:24]
  always @(posedge clock) begin
    if (reset) begin // @[src/main/scala/NTT.scala 22:21]
      regs_0 <= 32'h0; // @[src/main/scala/NTT.scala 22:21]
    end else if (regs_0 == 32'h1) begin // @[src/main/scala/NTT.scala 48:25]
      regs_0 <= 32'h0; // @[src/main/scala/NTT.scala 50:13]
    end else if (io_regWriteEn) begin // @[src/main/scala/NTT.scala 25:23]
      if (3'h0 == io_regWrAddr) begin // @[src/main/scala/NTT.scala 26:24]
        regs_0 <= io_regWrData; // @[src/main/scala/NTT.scala 26:24]
      end
    end
    if (reset) begin // @[src/main/scala/NTT.scala 22:21]
      regs_1 <= 32'h0; // @[src/main/scala/NTT.scala 22:21]
    end else if (addr_gen_io_finish) begin // @[src/main/scala/NTT.scala 69:28]
      regs_1 <= 32'h1; // @[src/main/scala/NTT.scala 70:13]
    end else if (regs_0 == 32'h1) begin // @[src/main/scala/NTT.scala 48:25]
      regs_1 <= 32'h0; // @[src/main/scala/NTT.scala 51:13]
    end else if (io_regWriteEn) begin // @[src/main/scala/NTT.scala 25:23]
      regs_1 <= _GEN_1;
    end
    if (reset) begin // @[src/main/scala/NTT.scala 22:21]
      regs_2 <= 32'h0; // @[src/main/scala/NTT.scala 22:21]
    end else if (_T_22) begin // @[src/main/scala/NTT.scala 176:48]
      regs_2 <= 32'h0; // @[src/main/scala/NTT.scala 179:13]
    end else if (_T_2 & regs_6 == 32'h2) begin // @[src/main/scala/NTT.scala 114:48]
      regs_2 <= 32'h0; // @[src/main/scala/NTT.scala 118:13]
    end else if (regs_2 == 32'h1 & regs_6 == 32'h1) begin // @[src/main/scala/NTT.scala 98:48]
      regs_2 <= 32'h0; // @[src/main/scala/NTT.scala 102:13]
    end else begin
      regs_2 <= _GEN_9;
    end
    if (reset) begin // @[src/main/scala/NTT.scala 22:21]
      regs_3 <= 32'h0; // @[src/main/scala/NTT.scala 22:21]
    end else if (read_done1a & _T_10) begin // @[src/main/scala/NTT.scala 208:42]
      regs_3 <= {{16'd0}, data_ram2_io_readData1}; // @[src/main/scala/NTT.scala 209:13]
    end else if (read_done1a & _T_3) begin // @[src/main/scala/NTT.scala 204:42]
      regs_3 <= {{16'd0}, data_ram1_io_readData1}; // @[src/main/scala/NTT.scala 205:13]
    end else if (io_regWriteEn) begin // @[src/main/scala/NTT.scala 25:23]
      regs_3 <= _GEN_3;
    end
    if (reset) begin // @[src/main/scala/NTT.scala 22:21]
      regs_4 <= 32'h0; // @[src/main/scala/NTT.scala 22:21]
    end else if (io_regWriteEn) begin // @[src/main/scala/NTT.scala 25:23]
      if (3'h4 == io_regWrAddr) begin // @[src/main/scala/NTT.scala 26:24]
        regs_4 <= io_regWrData; // @[src/main/scala/NTT.scala 26:24]
      end
    end
    if (reset) begin // @[src/main/scala/NTT.scala 22:21]
      regs_5 <= 32'h0; // @[src/main/scala/NTT.scala 22:21]
    end else if (io_regWriteEn) begin // @[src/main/scala/NTT.scala 25:23]
      if (3'h5 == io_regWrAddr) begin // @[src/main/scala/NTT.scala 26:24]
        regs_5 <= io_regWrData; // @[src/main/scala/NTT.scala 26:24]
      end
    end
    if (reset) begin // @[src/main/scala/NTT.scala 22:21]
      regs_6 <= 32'h0; // @[src/main/scala/NTT.scala 22:21]
    end else if (io_regWriteEn) begin // @[src/main/scala/NTT.scala 25:23]
      if (3'h6 == io_regWrAddr) begin // @[src/main/scala/NTT.scala 26:24]
        regs_6 <= io_regWrData; // @[src/main/scala/NTT.scala 26:24]
      end
    end
    if (reset) begin // @[src/main/scala/NTT.scala 42:25]
      start_bf <= 1'h0; // @[src/main/scala/NTT.scala 42:25]
    end else if (addr_gen_io_finish) begin // @[src/main/scala/NTT.scala 69:28]
      start_bf <= 1'h0; // @[src/main/scala/NTT.scala 71:14]
    end else begin
      start_bf <= _GEN_24;
    end
    if (reset) begin // @[src/main/scala/NTT.scala 43:28]
      read_done1a <= 1'h0; // @[src/main/scala/NTT.scala 43:28]
    end else begin
      read_done1a <= _T_22;
    end
    if (reset) begin // @[src/main/scala/NTT.scala 44:28]
      read_done1b <= 1'h0; // @[src/main/scala/NTT.scala 44:28]
    end else if (_T_22) begin // @[src/main/scala/NTT.scala 176:48]
      read_done1b <= 1'h0; // @[src/main/scala/NTT.scala 178:17]
    end else begin
      read_done1b <= start_bf;
    end
    if (reset) begin // @[src/main/scala/NTT.scala 45:27]
      read_done2 <= 1'h0; // @[src/main/scala/NTT.scala 45:27]
    end else begin
      read_done2 <= start_bf;
    end
  end
// Register and memory initialization
`ifdef RANDOMIZE_GARBAGE_ASSIGN
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_INVALID_ASSIGN
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_REG_INIT
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_MEM_INIT
`define RANDOMIZE
`endif
`ifndef RANDOM
`define RANDOM $random
`endif
`ifdef RANDOMIZE_MEM_INIT
  integer initvar;
`endif
`ifndef SYNTHESIS
`ifdef FIRRTL_BEFORE_INITIAL
`FIRRTL_BEFORE_INITIAL
`endif
initial begin
  `ifdef RANDOMIZE
    `ifdef INIT_RANDOM
      `INIT_RANDOM
    `endif
    `ifndef VERILATOR
      `ifdef RANDOMIZE_DELAY
        #`RANDOMIZE_DELAY begin end
      `else
        #0.002 begin end
      `endif
    `endif
`ifdef RANDOMIZE_REG_INIT
  _RAND_0 = {1{`RANDOM}};
  regs_0 = _RAND_0[31:0];
  _RAND_1 = {1{`RANDOM}};
  regs_1 = _RAND_1[31:0];
  _RAND_2 = {1{`RANDOM}};
  regs_2 = _RAND_2[31:0];
  _RAND_3 = {1{`RANDOM}};
  regs_3 = _RAND_3[31:0];
  _RAND_4 = {1{`RANDOM}};
  regs_4 = _RAND_4[31:0];
  _RAND_5 = {1{`RANDOM}};
  regs_5 = _RAND_5[31:0];
  _RAND_6 = {1{`RANDOM}};
  regs_6 = _RAND_6[31:0];
  _RAND_7 = {1{`RANDOM}};
  start_bf = _RAND_7[0:0];
  _RAND_8 = {1{`RANDOM}};
  read_done1a = _RAND_8[0:0];
  _RAND_9 = {1{`RANDOM}};
  read_done1b = _RAND_9[0:0];
  _RAND_10 = {1{`RANDOM}};
  read_done2 = _RAND_10[0:0];
`endif // RANDOMIZE_REG_INIT
  `endif // RANDOMIZE
end // initial
`ifdef FIRRTL_AFTER_INITIAL
`FIRRTL_AFTER_INITIAL
`endif
`endif // SYNTHESIS
endmodule
