program mining ;//


// by Estroma DMS-TFG 2016 -2017 Baseado em macro russo by Shinma the abyss

{$Include 'all.inc'}

const

//????? ? ??????? ? ??????? ?????????? ????

recallLine= '.recall 1,5';
recallLineMine= '.recall 1,6'; //Lost Ore Mine
//recallLineMine= '.recall 1,2';  //Monte BlackThorn
recallReg='.recall 1,5';
PickType = $0E85 ;
PickType2 = $0E86;
sunduk = $4007E892 ;//?????? ? ??????? ?????????? ?????? ? ????? ?????.
bagpickaxe = $4007E892 ;//????? ? ??????? ?? ??????? ????? ????? ??? ???????
bagjuvelir=$4007E892 ;//????? ? ??????? ? ??????? ?????????? ????? ???????
IDBoxIngot=$4007E892 ;  //ID ????? ??? ????? ?????? ? ?????? ?????
TinkerTool=$1EBC ;
ingot=$1BEF;
tong=$0FBB ;
savetime = 5000;
//????? ??????? X,Y
VozleX = 2512;
VozleY = 550;


MineX=  1236;
MineY= 1252;


zapasW=20; //???? ??? - zapasW = ??? ??? ??????? ????? ??????-?? ???????????? ? ???????

MaxIrons=200;
type
	minerio = record
		nome : string;
        cor: Cardinal;
		Quantidade: Integer;
	end;
var
aMinerios: array[1..20] of  minerio;
 BugOre:cardinal ;
initiabuffer: integer;
andou : boolean;
 MinTile:array of word;
i,j,h:word;
start_p: TFoundTile;
ctime : TDateTime;

checkW,Xmin,Xmax,Ymin,Ymax : Integer;
kol_mine:word;
 sum:word;    
 agump:word;
 arr_sum:word;
 rs:word;
 tfta:TFoundTilesArray;
 k:word;
 temp:TFoundTilesArray;
res_arr:TFoundTilesArray;
res_arrLimpo:TFoundTilesArray;
color: array of word;
DropOreType: array of word;
DropJuvType: array of word;
tyleType : word;
LastTile : TFoundTile;
totalores: integer;

procedure adicionaOreAtabela( cor:cardinal; qt:integer);
begin
for i:=1 to   20 do
begin
         if ( aMinerios[i].cor =  cor) then
         begin
           aMinerios[i].Quantidade := aMinerios[i].Quantidade + qt;
         end;
     
end;

end;

procedure montaArrayDeMinerios;
begin
//	nome : string;
//        cor: Cardinal;
//		Quantidade: Integer;
for i:=1 to   20 do
begin
          
     aMinerios[i].cor:=$999;  
     aMinerios[i].Quantidade:= 0
end;
aMinerios[1].nome:= 'Iron';
aMinerios[1].cor:= $0000 ;
aMinerios[2].nome:= 'Ceramic';
aMinerios[2].cor:= $00F2  ;
aMinerios[3].nome:= 'Rusty';
aMinerios[3].cor:=  $0750   ;
aMinerios[4].nome:= 'Old Copper';
aMinerios[4].cor:=  $0590;

aMinerios[5].nome:= 'Bronze';
aMinerios[5].cor:=  $06D6 ;
aMinerios[6].nome:= 'Dull Copper';
aMinerios[6].cor:=  $060A ;
aMinerios[7].nome:= 'Copper';
aMinerios[7].cor:=  $0641  ;
aMinerios[8].nome:= 'Shadow';
aMinerios[8].cor:=  $0770 ;
aMinerios[9].nome:= 'Agapite';
aMinerios[9].cor:=  $0400 ;
aMinerios[10].nome:= 'Rose';
aMinerios[10].cor:=   $0665 ;
aMinerios[11].nome:= 'Silver';
aMinerios[11].cor:=   $0231  ;

 aMinerios[12].nome:= 'Valorite';
aMinerios[12].cor:=   $0515   ;
 aMinerios[13].nome:= 'Verite';
aMinerios[13].cor:=   $07D1   ;
 aMinerios[14].nome:= 'Blood';
aMinerios[14].cor:=   $04C2  ;

 aMinerios[15].nome:= 'Black';
aMinerios[15].cor:=   $0455   ;
 aMinerios[16].nome:= 'Gold';
aMinerios[16].cor:=  $045E   ;
aMinerios[17].nome:= 'Myth';
aMinerios[17].cor:=   $052D   ;
aMinerios[18].nome:= 'Order';
aMinerios[18].cor:=  $024F   ;
aMinerios[19].nome:= 'Chaos';
aMinerios[19].cor:=   $03DF  
aMinerios[20].nome:= 'Ada!';
aMinerios[20].cor:=   $07A1    ;


  

end;
procedure printArryMinerios;
var porc:Double;
begin
    AddToSystemJournal('Total ores '+   inttostr(totalores));
for i:=1 to   20 do
begin
      
      if    (aMinerios[i].cor <> $999) and  (totalores <> 0) and (aMinerios[i].Quantidade <> 0) then   
      begin     
     // porc:=  ((aMinerios[i].Quantidade*100)/totalores);
     AddToSystemJournal('|'+aMinerios[i].nome+ '|'+inttostr(aMinerios[i].Quantidade) +'| MPM |'+inttostr(((aMinerios[i].Quantidade*1000000)/totalores))+'|');
     end;
end;

end;
procedure espalhaItems ( mtype :word) ;
var i :integer;
var xx: integer;
var yy:integer;
quantidade:integer;
begin
Uosay('cata tudo!');
quantidade:= Count(mtype);

for i:=0 to (quantidade/18)+1   do
      for xx:=0 to 3   do
     begin
       for yy:=0 to 3   do
       begin     
        if Count(mtype) = 0 then
        begin
        break;
        end;
        
        FindType (mtype,backpack);
        MoveItem(finditem,2,ground,GetX(self)-xx,GetY(Self)-yy,0);
        wait(200); 
        FindType (mtype,backpack);
        MoveItem(finditem,2,ground,GetX(self)+xx,GetY(Self)+yy,0);
        wait(200);
        end
     end;
begin
end; 
end;

function getMiningBufferUsage()  : integer;
var buffer: string;
var linha : string;
begin
ClearJournal;
UOSay('.buffer');
linha := Journal(0);
linha := copy(linha,Length(linha)-3,2); 
result :=  StrToInt(linha);
 end;      
 
procedure init;
begin

initiabuffer:=getMiningBufferUsage();
totalores:=1;
Xmin := getx(self)-10 - random(6);//////////////////////<<<<<<<<<<<<<<<<<       ??? ??? ?????????  ?????????? +/- ?,? ,
Xmax := getx(self)+10 +random(6);/////////////////////<<<<<<<<<<<<<<<<<        ????? ? ??????? ????????? ????? ?????
Ymin := gety(self)-10 +random(6);///////////////////////,<<<<<<<<<<<<<<<       ???????  ???????? ?? ????? ??????? ?? ? ????? ??????
Ymax := gety(self)+10 -random(6);///////////////////////<<<<<<<<<<<<<<<
//MinTile :=[561,562,563,564,565,566,567,568,569,570,571,572,573,574,575,576,577,578,579];
MinTile :=[557,556,559];
color:=[$052D,$00F2,$06D6,$07D1,$060A, $0641,$03DF,$0750,$024F,$0231,$07A1,$0665,$0515,$045E,$0590,$0770,$00F2,$0000,$04C2,$0400,$0455];
DropOreType:=[$19B9,$1779,$19B8,$19B7,$19BA];//????, ?????
DropJuvType:=[$3193,$3197,$3198,$0F2D,$0F15,$3195,$0F13,$0F26,$0F10,$3194,$0F25   ,$0F21,$0F19,$0F16,$3192,$0F11,$0F0F,$0F18,$0EED ];
end; 

 

Procedure saidaHelp1;
var andou:boolean;
begin
if ( Stam < 6 )then  
begin
wait(40000);
end;
while mana < 14 
do
begin
UseSkill ('meditation');
wait(10000);
end;
               
     andou:=NewMoveXY(5448,1193, True, 0, True);
     while (andou <> true )do
     begin 
     andou:=NewMoveXY(5448,1193, True, 0, True);
     end;
                  
Raw_Move(0,false);
Raw_Move(0,false);
Raw_Move(0,false);
UseObject (Backpack );
end;
procedure compraREG;
var local: integer;
begin
Wait(1000);
AutoBuy($0F7B,$0000,15);
AutoBuy($0F7A,$0000,15);
AutoBuy($0F86,$0000,15);
//AutoBuy($0F8C,$0000,3);
//AutoBuy($0F8D,$0000,3);
//restoca
UseObject($001263CB  );
wait(2000); 
//acha mandrake
while count($0F86)  < 10 do
begin 
uosay('buy');
Wait(6000);

local :=  Random (4);
case local of
  0 : NewMoveXY(2511,595, True, 0, True);
  1 : NewMoveXY(2510,598, True, 0, True);
  2 : NewMoveXY(2506,598, True, 0, True);
  3 : NewMoveXY(2507,595, True, 0, True);
end; 
end;

 UseObject($001263CB  );
wait(2000); 
//acha 


//FindType($0F7B,backpack) ;
while count($0F7B) < 10 do
begin 
uosay('buy');
Wait(6000);
local :=  Random (4);
case local of
  0 : NewMoveXY(2511,595, True, 0, True);
  1 : NewMoveXY(2510,598, True, 0, True);
  2 : NewMoveXY(2506,598, True, 0, True);
  3 : NewMoveXY(2507,595, True, 0, True);
end; 
end;

 UseObject($001263CB  );
wait(2000); 
//acha mandrake

while count($0F7A) < 10 do
begin 
uosay('buy');
Wait(6000);

local :=  Random (4);
case local of
  0 : NewMoveXY(2511,595, True, 0, True);
  1 : NewMoveXY(2510,598, True, 0, True);
  2 : NewMoveXY(2506,598, True, 0, True);
  3 : NewMoveXY(2507,595, True, 0, True);
end; 
end;

end;

procedure compraregMinocSemHelp;
var andou:boolean;
begin

Wait(savetime);

  // while FindQuantity = 0
    andou:=NewMoveXY(2508,596, True, 0, True);
    while andou = false do
      begin 
       UOSay (recallReg);
      Wait(10000)
      andou:=NewMoveXY(2508,596, True, 0, True);
      ClearBadLocationList;
      //andou:=NewMoveXY(5400,1199, True, 0, True);
     end;
Wait(savetime);
compraREG;
  
     

Wait(savetime);           

end;
procedure compraregMinoc;
var andou:boolean;
begin
//
  //FindTypeEx($0F7B,$0000,backpack,false); 
       // while FindQuantity = 0
  //  andou:=NewMoveXY(5400,1199, True, 0, True);
  //  while andou = false do
 //     begin 
  //   andou:=NewMoveXY(5400,1199, True, 0, True);
 //    end;
Wait(savetime);
//compraREG;
//  andou:=NewMoveXY(5411,1201, True, 0, True);
// while andou = false   do
//    begin 
//     andou:=NewMoveXY(5411,1201, True, 0, True);
//     end;
     
andou:=NewMoveXY(2330,537, True, 0, True);
 while andou = false   do
    begin    
     andou:=NewMoveXY(2330,537, True, 0, True);
     if andou = true then
     begin
     break;
     end;
     //andou:=NewMoveXY(5393,1206, True, 0, True);
     end;
Wait(savetime);           
Raw_Move(0,false);
Raw_Move(0,false);
Raw_Move(0,false); 
  // while FindQuantity = 0
    andou:=NewMoveXY(2508,596, True, 0, True);
    while andou = false do
      begin
   ;
      andou:=NewMoveXY(2508,596, True, 0, True);
      ClearBadLocationList;
      //andou:=NewMoveXY(5400,1199, True, 0, True);
     end;
Wait(savetime);
compraREG;
  
     

Wait(savetime);           

end;

procedure compraregHelp;
var andou:boolean;
begin
//
  //FindTypeEx($0F7B,$0000,backpack,false); 
      
  // while FindQuantity = 0
    andou:=NewMoveXY(5400,1199, True, 0, True);
    while andou = false do
      begin 
     andou:=NewMoveXY(5400,1199, True, 0, True);
     end;
Wait(savetime);
compraREG;
  andou:=NewMoveXY(5411,1201, True, 0, True);
 while andou = false   do
    begin 
     andou:=NewMoveXY(5411,1201, True, 0, True);
     end;
     
andou:=NewMoveXY(5411,1203, True, 0, True);
 while andou = false   do
    begin 
     andou:=NewMoveXY(5411,1203, True, 0, True);
     end;
Wait(savetime);           
Raw_Move(2,false);
Raw_Move(2,false);
Raw_Move(2,false);
end;
procedure voltadaHelp;
begin
saidaHelp1
end;
procedure checkPerdido;
begin
if (GetX(Self)-100 < MineX) or  (GetY(Self)-100  < MineY) then
begin
UOSay (recallLine);
Wait(16000);
end


end;
procedure ress;
begin

SetWarMode (false);
Wait(1000);
HelpRequest ;
Wait(1000); 
NumGumpButton(GetGumpsCount-1,7);
Wait(1000);
NumGumpButton(GetGumpsCount-1,8);
if not dead  then
begin
Wait(90000);
end;

Wait(40000);

voltadaHelp;
//compraregHelp;
compraregMinoc;
Wait(4000);
UOSay (recallLine);
Wait(12000);

end; 

procedure GotoOnHome;
var
andou : boolean;
begin
//RECALL AQUI

//RECALL AQUI
//h:=0;
//while ((GetX(self)<>vozleX) and (GetY(self) <> vozleY))and (Not dead)and (h<5) do
//   begin

//semReg 


if (Count($0F7B) < 1) or (Count($0F7A)<1) or( Count($0F86) <1) then
begin
ress;
end;
    checklag(600);
//   AddToSystemJournal('175 ??? ?????');
                
     andou:=NewMoveXY(vozleX,vozleY, True,1, True);
     while (andou <> true ) and not dead do
     begin 
     while ( mana < 67) and not dead  do
     begin
     UseSkill('meditation');
     wait(2000);
     end;
     UOSay (recallLine);
     Wait(12000);
     
     andou:=NewMoveXY(vozleX,vozleY, True,1, True);
     end;
     Uosay('banker bank guards')
     wait(100)
   // NewMoveXY(vozleX,vozleY, True, 1, True);
    //abre aa bag 
    UseObject(BugOre);  
    UseObject(BugOre);
        if Dead then
              begin
               ress; 
               Wait(2000);
                UOSay (recallLine);
                Wait(12000);
              end;
 //   h:=h+1
//end;
end;

Procedure CheckItem;  //???????? ???????, ?????? ????,?????.
begin
///////////////
//  Check Ingot
///////////////
  checklag(50000);
  FindType(ingot,backpack);
  while FindFullQuantity <50 do
  begin    
   If  IDBoxIngot = 0 then 
     begin
     AddToSystemJournal('???? ?????????');
     repeat           
     wait(1000); 
     FindType(ingot,backpack);
     until (FindFullQuantity > 50) or dead;
    end  
    else    
    begin
    checklag(50000);
    UseObject(IDBoxIngot);
    wait(2000);   
    FindType(ingot,IDBoxIngot);    
          if FindFullQuantity > 20 then
            begin
              checklag(50000);
              AddToSystemJournal('???-?? ?????= '+Inttostr(FindFullQuantity));
              MoveItem(Finditem,20,backpack,0,0,0);
            end
             else
            begin
              AddToSystemJournal('???? ?????????');
              repeat           
             // wait(10000);
              if Dead then
              begin
               ress; 
               Wait(2000);
                UOSay (recallLine);
                Wait(12000);
              end;
              checklag(50000);
              UseObject(IDBoxIngot);
              wait(2000);
              FindType(ingot,IDBoxIngot);
              until  dead or (FindFullQuantity > 50);
            end;     
    end;
  end;
//////////////////
//    Check TinkerTools
/////////////////
checklag(50000);
while count(TinkerTool) <1 do
  begin 
  checklag(50000);
  UseObject(IDBoxIngot);
  wait(2000);
  FindType(TinkerTool,IDBoxIngot);
  //grab(TinkerTool,1);  
  MoveItem(Finditem,1,backpack,0,0,0);
  end;     
  checklag(50000);
  UseObject(IDBoxIngot);
  wait(2000);  
  FindType(TinkerTool,IDBoxIngot); 
  while finditem <2 do
    begin
      for i:=0 to  GetGumpsCount do 
      begin      
      wait(500);
      CloseSimpleGump(i); 
      end;
  agump := GetGumpsCount;
  checklag(50000);
  UseObject(FindType(TinkerTool,Backpack)); 
    wait(1000);
    while agump = GetGumpsCount do
     Wait(60);     
    NumGumpButton(GetGumpsCount-1,9002);
    wait(600);
    NumGumpButton(GetGumpsCount-1,11); 
    AddToSystemJournal('???????? ?????? ???');
    wait(5000);   
     MoveItems(backpack, TinkerTool, $FFFF, IDBoxIngot, 0,0,0, 700);
    FindType(TinkerTool,IDBoxIngot);
  end;
  

for i:=0 to  GetGumpsCount do 
  begin      
  wait(500);
  CloseSimpleGump(i); 
  end;    
 
end;

procedure PickAxeInBug;
begin
if not dead and (count(TinkerTool)>0) then
begin
  NewMoveXY( 869,1244,true,0,true);
  checklag(60000);
  useobject(IDBoxIngot);
  wait(1000);
while not dead and (count(TinkerTool)>0) do    
  begin   
  checklag(60000);
  FindType(TinkerTool,BackPack);
  MoveItem(Finditem,0,IDBoxIngot,0,0,0);
  wait(600);
  AddToSystemJournal('1');
  end;    
  end;

if  not dead and (count(ingot)>0) then
begin  
  NewMoveXY( 869,1244,true,0,true);
  checklag(60000);
  useobject(IDBoxIngot);
  wait(1000);
 while not dead and (count(ingot)>0) do    
  begin
  NewMoveXY( 869,1244,true,0,true);
  FindType(ingot,BackPack); 
  checklag(60000);
  MoveItem(Finditem,0,IDBoxIngot,0,0,0);
  wait(600); 
  AddToSystemJournal('2');
  end;
  end;
  
  if not dead and (count(PickType)>10) then
  begin  
  NewMoveXY( 869,1244,true,0,true);
  checklag(60000);
  useobject(sunduk);
  wait(1000); 
  while not dead and (count(PickType)>10) do    
  begin
  FindType(PickType,BackPack);     
  MoveItem(Finditem,1,bagpickaxe,0,0,0);
  wait(600); 
  AddToSystemJournal('3');
  end; 
  end; 
  
end;

procedure CreapePick;
begin
while  (count(TinkerTool)>0) and not dead and (count(PickType)<60) do
  begin   
  for i:=0 to  GetGumpsCount do 
    begin      
    wait(500);
    CloseSimpleGump(i); 
    end;
    agump := GetGumpsCount;
    checklag(50000);
    UseObject(FindType(TinkerTool,Backpack)); 
    wait(1000);
    while agump = GetGumpsCount do
    Wait(60);     
    NumGumpButton(GetGumpsCount-1,9002);
    wait(600);
    NumGumpButton(GetGumpsCount-1,24); 
    AddToSystemJournal('?????? PickAxe');
    wait(1000);   
    
    checkW:=0;
    while not dead and connected and IsObjectExists(FindItem) and (count(ingot)>50) and (count(PickType)<10) do  
    begin   
      checklag(50000); 
      WaitGump('1999');
      Wait(700);
      checkW:=checkW+1;
      if checkW = 20 then 
        begin
        UseObject(FindType(TinkerTool,Backpack)); 
        checkW:=0;
        end;
    end;    
  
  end;

end;

procedure fabricaPick;
begin
CheckLag (5000);
FindTypeEx($19B9,0000,bugore,false);
//EXFindType(PickType,bagpickaxe);
Grab(FindItem, 4);
wait(1000);
 //vai ate a forja    
                
                FindDistance:= 20;
            FindType($19A2,ground);
            newMoveXY (GetX(finditem),getY(finditem),true,1,true);
            //
UseObject(FindTypeEx($19B9,0000,backpack,false));
wait(1000);
UseObject(FindType(TinkerTool,Backpack)); 
AutoMenu ('Tinkering','Tools');
AutoMenu ('Tools','pickaxe');
WaitJournalLine(NOW,'You put',4000);

end;

Procedure GiveMePick;
var
agump:Word;
begin
                     GotoOnHome;
   //////////////////////////////////////////////////////////////
///////////???????? ????? ??? ???????, ???? ??? ?????????
///////////////////////////////////////////
//AddToSystemJournal('???????? ?????');
  if count(PickType) < 1 then 
      begin      
      GotoOnHome;      
       UseObject(bagpickaxe);  
    UseObject(bagpickaxe);
      
      finddistance := 2;
      //checklag(60000);
     // useobject(sunduk);
     // wait(1000);
      checklag(600);
      useobject(bagpickaxe);
      wait(100);
         
        while  (count(PickType)<1 ) and (ObjAtLayer(RHandLayer ) = 0 ) do  
          begin  
           fabricaPick;
            GotoOnHome;
          checklag(60000);
          useobject(BugOre);
          useobject(BugOre);
          FindType(PickType,BugOre);
          Grab(FindItem, 1);  
              AutoMenu ('axle','Tool');
            AutoMenu ('10 B','pickaxe');
            
                   UseType ($1EBC,$00000);
        
               WaitJournalLine(NOW,'Voce falhou|you put',3000);
           
                   FindType(PickType,bagpickaxe);
                   AutoMenu ('axle','Tool');
            AutoMenu ('10 B','pickaxe');
                   UseType ($1EBC,$00000);
        
          WaitJournalLine(NOW,'Voce falhou|you put',3000);
          
          
            if Dead then
              begin
               ress; 
               Wait(2000);
                UOSay (recallLine);
                Wait(12000);
              end; 
          wait(900);  
          if FindCount<1 then 
            begin 
              ////////////////////////////////////////
              if Dead then
              begin
               ress; 
               Wait(2000);
                UOSay (recallLine);
                Wait(12000);
              end;
            
             //CheckItem;
            
            // CreapePick;
            
            // PickAxeInBug;
            
            end;
          end;

          end;     

end;

procedure DropOre;
var g,hh : integer;
oreid,oresundukid: Cardinal;
Begin

finddistance := 2;

                   UOSay('banker bank guards');
                   wait(3000);
///////////////////////////////////
/////????????? ????
//////////////////////////////
      useobject(BugOre);
      wait(100);  
//AddToSystemJournal('????????? ????');
for hh := 0 to  (Length(DropOreType)-1) do
  for g := 0 to  (Length(color)-1) do
    begin
   
    FindTypeEx(DropOreType[hh],color[g],backpack,false);
    Wait(100);
    if FindCount > 0 then
      begin      
      totalores:=  totalores+ FindQuantity; 
      adicionaOreAtabela( color[g], FindQuantity);
      GotoOnHome;        
      checklag(600);
      //useobject(sunduk);
     // wait(1000);
      checklag(600);
    
      h:=0;
      oreid:=FindItem ;
        repeat
        checklag(600);
        //  AddToSystemJournal('196');
        oreid:=FindTypeEx(DropOreType[hh],color[g],backpack,false); 
          if  (FindQuantity = 0) or  (FindQuantity > 59000) then      
          begin
          MoveItem(oreid,0,BugOre,0,0,0) ;  
          end
          else   
          begin
          MoveItem(oreid,0,BugOre,0,0,0);     
       
          end ;    
          
          wait (900);
          h:=h+1; 
          FindTypeEx(DropOreType[hh],color[g],backpack,false);
        until (FindCount = 0) or dead or(h>30);
      end;
     
    end;
 // useobject(sunduk);
 // wait(1000);        
          
//////////////////////////////////////////////////////
///////????????? ?????
///////////////////////////////////
//AddToSystemJournal('????????? ?????');
  for hh := 0 to  (Length(DropJuvType)-1) do
    begin
    checklag(60000);
    FindTypeEx(DropJuvType[hh],$FFFF,backpack,false);
      if FindCount > 0 then
        begin      
        checklag(60000);
        checklag(60000);
        useobject(bagjuvelir);
        wait(1000);
        h:=0;
        oreid:=FindItem ;
          repeat
          checklag(60000);
          //  AddToSystemJournal('196');
          oresundukid:=FindTypeEx(DropJuvType[hh],$FFFF,BugOre,false); 
            if  FindCount = 0 then
            MoveItem(oreid,0,BugOre,0,0,0)
            else
            MoveItem(oreid,0,BugOre,0,0,0);
          wait (900);
          h:=h+1; 
          FindTypeEx(DropJuvType[hh],$FFFF,backpack,false);
          until (FindCount = 0) or dead or(h>30);
        end;
    end;
      AddToSystemJournal('total ores: '+inttostr(totalores)); 
      AddToSystemJournal('Buffer Gasto: '+inttostr(initiabuffer));
     // AddToSystemJournal('Fator raridade: '+inttostr((totalores)/(initiabuffer)));
      
     //initiabuffer
   GiveMePick;
    printArryMinerios;
   
End;

procedure gotoshahta;
begin

//    AddToSystemJournal('gotoshahta');
    //checksave;     
                
     andou:=NewMoveXY(MineX,MineY, True,1, True);
     while (andou <> true ) and not dead  do
     begin   
     while ( mana < 67) and not dead  do
     begin
     UseSkill('meditation');
     wait(2000);
     end;
      UOSay (recallLineMine);
      Wait(10000);
     andou:=NewMoveXY(MineX,MineY, false,1, True);
     end;
  
    checklag(60000);
    NewMoveXY(MineX,MineY, true, 2, True);
end;

procedure checkweight;
var stackSize: integer;
begin


stackSize :=Count ($19B9);
if (Weight>=(MaxWeight-zapasW)) or (stackSize >= MaxIrons) and not dead then
   begin  
   checksave;
   GotoOnHome;
   DropOre;
    printArryMinerios;   
    if (Count(  $0F7B ) <10 or Count( $0F86 )  or Count($0F7A))  then
    begin
    compraregMinocSemHelp;
    end;
   gotoshahta;
   end;
end;


function sqr(a:LongInt):LongInt;
begin
 result:=a*a;
end;


function vector_length(c_1,c_2:TFoundTile):LongInt;
begin
 result:=Round(sqrt(sqr(c_1.X-c_2.X)+sqr(c_1.Y-c_2.Y)));
end;


procedure QuickSort(var item:TFoundTilesArray; count:integer; point:TFoundTile);
var
 temp_index,temp_value, tempo,i,j:LongInt;
 t_c:TFoundTile;
begin
 kol_mine:=count;    
//  AddToSystemJournal('60 ????????? ?????? '+IntToStr(count)+' ');
 t_c:=point;
 temp_index:=0;
 temp_value:=vector_length(t_c,item[temp_index]);
 for i:=0 to count-2 do
 begin
  for j:=i to count-1 do
   begin
    tempo:=vector_length(t_c,item[j]);
    if tempo<temp_value then
     begin
      temp_index:=j;
      temp_value:=tempo;
     end;
   end;
   t_c:=item[temp_index];
   item[temp_index]:=item[i];
   item[i]:=t_c;
   temp_value:=vector_length(item[i],item[i+1]);
 end;
 i:=0;
end; 

procedure cancel;
begin
CloseMenu;
CancelMenu; 
If TargetPresent Then CancelTarget; 
end;

procedure poisk_ore;
var
tFound : TFoundTile ;
tile : TMapCell;
stat: TStaticCell;
//Statics : Array of TStaticItem;
begin
  SetArStatus(true);
  init;
  sum:=0;  
  arr_sum:=0;
  rs:=0;
  j:=0 ; 
   for i:=0 to (Length(MinTile)-1) do
    begin   
     //rs:=GetStaticTilesArray(Xmin,Ymin,Xmax,Ymax,WorldNum,MinTile[i],tfta);
     //rs:=GetStaticTileData (Xmin,Ymin,Xmax,Ymax,WorldNum,MinTile[i],tfta); 
      rs:=GetLandTilesArray (Xmin,Ymin,Xmax,Ymax,WorldNum,MinTile[i],tfta); 
       AddToSystemJournal('tile:' + IntToStr(MinTile[i]) + ' ENCONTRADO:'+IntToStr(rs));
      //tyleType := rs.Tile;
     if rs>0 then 
     
      for k:=0 to rs-1 do
      begin 
      tFound := tfta[k];
      tile:=GetMapCell(tfta[k].X,tfta[k].Y,WorldNum );
     stat:= ReadStaticsXY(tfta[k].X,tfta[k].Y,WorldNum );
     // Statics := stat.Statics ;
      AddToSystemJournal(inttostr(k)+' MOntando mapaX = '+inttostr(tFound.X)+' y='+inttostr(tFound.Y) + ' Z='+inttostr(tFound.Z)+ ' Tile='+inttostr(tFound.Tile)+'|'+inttostr(tile.Tile)+'|'+inttostr(tile.Z));       
      if stat.StaticCount  > 0 then
      begin
      temp[arr_sum+k]:=tfta[k];
      end
      else
      begin
      end;
      
      end;
     arr_sum:=arr_sum+rs; 
     end;
      AddToSystemJournal('arr_sum = '+inttostr(arr_sum)); 
      QuickSort(temp,arr_sum,start_p);    
      //AddToSystemJournal('QuickSort 2');
      for k:=0 to arr_sum-1 do 
      res_arr[sum+k]:=temp[k];
      sum:=sum+arr_sum; 
      //AddToSystemJournal('exit'); 
      
     if arr_sum<=0 then     
      begin
      AddToSystemJournal('Object not found');  
      wait(360000);
      end;
     // end;
end;


procedure Wait_Target(time_ms:Cardinal);
var
 TempTime,SumTime:Cardinal;
begin      
 SumTime:=0;
 repeat
  checklag(60000);
  wait(500);
  TempTime:=Timer;
  SumTime:=SumTime+(Timer-TempTime);
 until ((targetpresent) or (dead) or (not connected) or (SumTime>time_ms));
end;

procedure kirka;
begin

if   (count(PickType)>0) and (Weight<=(MaxWeight-zapasW)) or (gettype(ObjAtLayer(RhandLayer)) > 0) then
  begin
  usetype(PickType,$FFFF);
  end  
  else
  begin 
  givemepick;
  end;  
   
 h:=0; 
  
  ctime := Now;
  repeat
  //AddToSystemJournal('338');
   wait(200);
  
   //check_war;
   h:=h+1;
  // AddToSystemJournal('352 h = '+inttostr(h));
  until  (InJournalBetweenTimes('You can''t mine that|no metal|in your backpack|Target cannot be seen|There is nothing|You loosen|no line of sight|You cannot mine so close to yourself|Targeting cancelled|That is too far away|You put|Voce nao encontrou|Try mining|is attacking you|You have been', ctime, Now)>= 0)or dead or (h>60) or (Weight>=(MaxWeight-zapasW)); 
       
     if InJournal('is attacking you')>0 then
 begin
   //espalhaItems($19B9);
  // clearjournal; 
   
 end; 
 
  //UOSay('guards');
 // until  (InJournalBetweenTimes('You can''t mine that|no metal|Target cannot be seen|There is nothing here to mine|no line of sight|You cannot mine so close to yourself|Targeting cancelled|That is too far away', ctime, Now)>= 0)or dead or (h>20) or (Weight>=(MaxWeight-zapasW));
  //CharDead;  
  
    if not Hidden then
              begin  
             // uosay('guards');
             // UseSkill('hiding'); 
            //  wait(4000);
            //  UseSkill('stealth');
             //  wait(4000);
              end
              else
              begin
             //  UseSkill('stealth'); 
              end;   
              
                                  {
              //pega ores do chao
			  FindDistance:= 20;
			FindType($19B9 ,ground);
			if FindCount > 0 then
			begin  
				NewMoveXY(getX(FindItem),getY(FindItem),true,1,true);
				MoveItem(Finditem,0,backpack,0,0,0);   
                wait(1000); 
			end;  
            FindType($19B8  ,ground);
			if FindCount > 0 then
			begin  
				NewMoveXY(getX(FindItem),getY(FindItem),true,1,true);
				MoveItem(Finditem,0,backpack,0,0,0);
                wait(1000);    
			end; 
            FindType($19BA  ,ground);
			if FindCount > 0 then
			begin  
				NewMoveXY(getX(FindItem),getY(FindItem),true,1,true);
				MoveItem(Finditem,0,backpack,0,0,0);
                wait(1000);    
			end; 
              FindType($19B7   ,ground);
			if FindCount > 0 then
			begin  
				NewMoveXY(getX(FindItem),getY(FindItem),true,1,true);
				MoveItem(Finditem,0,backpack,0,0,0);
                wait(1000);    
			end;       
				FindDistance:= 2;
            //  FindTypeEx($19B9,-1,Ground,false);
            //    MoveItem(Finditem,0,backpack,0,0,0);
            }
          
end;


Procedure DoitBaby(f_tile:TFoundTile);
begin 
 //AddToSystemJournal('type ='+inttostr(f_tile.Tile) + 'X='+inttostr(f_tile.X)+'Y='+inttostr(f_tile.Y)) ; 
   if LastTile <> f_tile then
   begin
   LastTile:= f_tile;
   end
   else 
   begin
   exit;
   end;         
h:=0;

  //repeat
  cancel;    
  //  AddToSystemJournal('Me X='+inttostr(GetX(self))+' Y= '+inttostr(GetY(self))+ ' Z= '+inttostr(GetZ(self)));
 // AddToSystemJournal('target X='+inttostr(f_tile.X)+' Y= '+inttostr(f_tile.Y)+' Z= '+inttostr(f_tile.Z)) ;
    //WaitTargetXYZ (f_tile.X,f_tile.Y,f_tile.Z);  
    //UOSay('guards');
    WaitTargetXYZ (f_tile.X,f_tile.Y,0);
    
    kirka;
  
//fix pab
    WaitTargetXYZ (GetX(Self)-1,GetY(Self)-1,0);
    kirka;       
   
    
    WaitTargetXYZ (GetX(Self),GetY(Self)-1,0);
    kirka;       

                             
    WaitTargetXYZ (GetX(Self)-1,GetY(Self),0);
    kirka;
  
    
    WaitTargetXYZ (GetX(Self)+1,GetY(Self)+1,0);
    kirka;
   
    
    WaitTargetXYZ (GetX(Self),GetY(Self)+1,0);
    kirka;
 
                                    
    WaitTargetXYZ (GetX(Self)+1,GetY(Self),0);
    kirka;
 
    
    WaitTargetXYZ (GetX(Self)-1,GetY(Self)+1,0);
    kirka;
   
                                    
    WaitTargetXYZ (GetX(Self)+1,GetY(Self)-1,0);
    kirka;
  
    //fix Pab

end;
//////

/////

 procedure Unicode_Speech(text,SenderName : String; SenderID : Cardinal); 
 begin 
// AddToSystemJournal('Event! Unicode Speech: SenderID = $'+ IntToHex(SenderID,8) + ' ; SenderName =  ' + SenderName + '; SenderText : ' + text);  
//if  text='The world will save in 5 seconds.' then
//begin
//wait(1500);
//end;

 end; 

///////////////////////////////////////////
BEGIN
 MoveOpenDoor :=true; 
 montaArrayDeMinerios;
 if CHARNAME = 'MinasGerais' then
begin
BugOre:=$40076FBC ;
end;
if CHARNAME = 'Cyf Knight' then
begin
BugOre:=$4007E892  ;
end;
if CHARNAME = 'Estroma' then
begin
BugOre:=$400F250E   ;
end;
 if Dead then
 begin   
 ress;
 end;
 if ((Count(  $0F7B ) < 3) or( Count(  $0F7A ) < 3) or (Count(  $0F86 ) < 3)  )  then
    begin
compraregMinocSemHelp;
end;
 gotoshahta;   
 poisk_ore;
 while true do

begin
//ress;
AddToSystemJournal('BEGIN');

SetPauseScriptOnDisconnectStatus(True);
SetARStatus(True);
//PickAxeInBug;

 if (Count(  $0F7B ) < 3)  then
    begin
compraregMinocSemHelp;
end;

GotoOnHome;

GiveMePick;
checkweight;
SetEventProc(evUnicodeSpeech,'Unicode_Speech');
//SetEventProc(evSpeech,'Unicode_Speech');  

//poisk_ore;
while not dead   do
begin
setwarmode(false);
//AddToSystemJournal('poisk_ore');

//ClearDuplicate;
//AddToSystemJournal('poisk_ore_exit');
j:=sum-1;
i:=0;
while (i<j)and(Not Dead) do
begin
  
gotoshahta;
ClearBadLocationList;
//AddToSystemJournal('372 I ==== '+inttostr(i));
            if i< kol_mine then 
            begin
            //  Addtosystemjournal('376');  
          while (res_arr[i].X=0)and(res_arr[i].Y=0)do 
          i:=i+1;
          
          checkweight;
                 if not Hidden then
              begin  
             // uosay('guards');
            //  UseSkill('hiding'); 
            //  wait(4000);
            //  UseSkill('stealth');
           //    wait(4000);
              end
              else
              begin
              // UseSkill('stealth'); 
              end;   
        
             // AddToSystemJournal('Ponto Numero:'+inttostr(i) );           
                       andou:=   NewMoveXY(res_arr[i].X,res_arr[i].Y,true,2,true); 
              if not Hidden then
              begin  
              //WaitJournalLine(NOW,'You Failed|You have',3000);
              end; 
            //  Addtosystemjournal('379');
                 if andou then
                 begin
                  DoitBaby(res_arr[i]);
                 end
                 else 
                 begin 
                    while (andou <> true ) and (i<j) do
                    begin
                     i := i+1; 
                     andou:=   NewMoveXY(res_arr[i].X,res_arr[i].Y,true,2,true)
                    //  AddToSystemJournal(' Pulando Ponto Numero:'+inttostr(i) ); 
                     end;
                    if i>=kol_mine then 
                    begin
                    i:=0;
                    //fix trav longe da mina
                          gotoshahta
                    end;
                  end;
       //           Addtosystemjournal('385');
                 i:=i+1;
          //       AddToSystemJournal('387 I  === '+inttostr(i));  
            
            if i>= (kol_mine - 1) then 
                 begin
                i:=0;
                while (res_arr[i].X=0)and(res_arr[i].Y=0)do   
                i:=i+1;
                checkweight;     
                 andou:=   NewMoveXY(res_arr[i].X,res_arr[i].Y,true,1,true);   
                   DoitBaby(res_arr[i]);
                   i:=i+1;
                  AddToSystemJournal('???????? ??? ? ?????? i:= '+inttostr(i));
                 
                  end;
             end;
             
        
         //                   AddToSystemJournal('403 I === '+inttostr(i));                              
checkweight;  
end;
end;
 PlayWav('cartoon012.wav');
ress;
Wait(4000);
UOSay (recallLine);
Wait(12000);
    UseObject(bagpickaxe);  
    UseObject(bagpickaxe)
end;




END.