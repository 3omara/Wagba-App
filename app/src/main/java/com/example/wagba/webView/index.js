const firebaseConfig = {
    apiKey: "AIzaSyBboK5zQoSH01s6gaP35JWnipEQsk8d8ak",
  authDomain: "wagba-73673.firebaseapp.com",
  databaseURL: "https://wagba-73673-default-rtdb.firebaseio.com",
  projectId: "wagba-73673",
  storageBucket: "wagba-73673.appspot.com",
  messagingSenderId: "597282463136",
  appId: "1:597282463136:web:8a3c2fade1569154ced8c5",
  measurementId: "G-WBMXHTNDVC"
};

firebase.initializeApp(firebaseConfig);
const ordersRef = firebase.database().ref().child('Orders');;

ordersRef.on("value", (snapshot) => {
  const data = snapshot.val();
  showOrders(data);
});

function tableHeader(){
  var headers = ["Restaurant", "Order Items", "Delivery Gate", "Delivery Time", "Order Total Cost", "Order Status", "Pay Status"];
  var headerRow = document.createElement("tr");
  for(var i =0; i<headers.length; i++){
    headerData = document.createElement("th");
    headerData.innerHTML = headers[i];
    headerRow.appendChild(headerData);
  }
  return headerRow;
}


function showOrders(data){
    var table = document.getElementById("orders_table");
    table.innerHTML = "";
    var headerRow = tableHeader();
    table.appendChild(headerRow);
  
    Object.keys(data).forEach((key) => {
        var ord_row = document.createElement("tr");
        ord_row.id = key;

        var rowData = document.createElement("td");
        rowData.className = "restaurantName";
        var nameDiv = document.createElement("div");
        nameDiv.className = "singleLine";
        nameDiv.innerHTML = data[key].restaurantName;
        
        rowData.appendChild(nameDiv);
        
        ord_row.appendChild(rowData);

        var itemsField = document.createElement("td");
        itemsField.id = "orderItems"+key;
        itemsField.className = "orderItems";
        var div = document.createElement("div");
        div.className = "scrollable";
        var list = document.createElement("ul");
        var items = data[key].items;
        Object.keys(items).forEach((itemName)=>{
          var listItem = document.createElement("li");
          listItem.innerHTML = items[itemName] + " " + itemName;
          list.appendChild(listItem);
        })
        div.appendChild(list);
        itemsField.appendChild(div);
        ord_row.appendChild(itemsField);

        var atrs = new Map([["gate", data[key].gate.toString()], 
                            ["timeSlot", data[key].timeSlot], 
                            ["price", data[key].price.toString()]]);

        atrs.forEach (function(value, mapKey) {
            var rowData = document.createElement("td");
            rowData.innerHTML = value;
            rowData.className = mapKey;
            ord_row.appendChild(rowData);
            })

        orderStatusField = document.createElement("td");
        orderStatus = document.createElement("select");
        ord_options = ["Cancelled","Pending", "Confirmed", "In Delivery", "Delivered"];
        for(var i =0; i<ord_options.length; i++){
          option = document.createElement("option");
          option.innerHTML = ord_options[i];
          orderStatus.appendChild(option);
        }
        orderStatus.selectedIndex = ord_options.findIndex((option)=>{return option==data[key].deliveryStatus;});
        
        if(orderStatus.selectedIndex==0){
          orderStatus.disabled = true;
        }else{
          orderStatus.addEventListener("change", function(){
            var state = this.value;
            firebase.database().ref('Orders/'+key).set({
              date: data[key].date,
              gate: data[key].gate,
              items: data[key].items,
              payStatus: data[key].payStatus,
              price: data[key].price,
              restaurantName: data[key].restaurantName,
              timeSlot: data[key].timeSlot,
              userID: data[key].userID,
              deliveryStatus: state
            });
          });
        }
        orderStatusField.appendChild(orderStatus);
        ord_row.appendChild(orderStatusField);

        

        payStatusField = document.createElement("td");
        payStatus = document.createElement("select");
        pay_options = ["Paid", "Payment due"];
        for(var i =0; i<pay_options.length; i++){
          option = document.createElement("option");
          option.innerHTML = pay_options[i];
          payStatus.appendChild(option);
        }
        payStatus.selectedIndex = pay_options.findIndex((option)=>{return option==data[key].payStatus;});
        
        if(payStatus.selectedIndex==0){
          payStatus.disabled = true;
        }else{
          payStatus.addEventListener("change", function(){
            var state = this.value;
            firebase.database().ref('Orders/'+key).set({
              date: data[key].date,
              gate: data[key].gate,
              items: data[key].items,
              payStatus: state,
              price: data[key].price,
              restaurantName: data[key].restaurantName,
              timeSlot: data[key].timeSlot,
              userID: data[key].userID,
              deliveryStatus: data[key].deliveryStatus
            });
          });
        }
        payStatusField.appendChild(payStatus);
        ord_row.appendChild(payStatusField);

        table.appendChild(ord_row);
      });

    
}