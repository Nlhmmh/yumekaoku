import constant from "./constant";
import http from "./http";

 const formatCurrency = (value) => {
    let v;
    if (value && Number(value) >= 0) {
      v = Number(value);
    } else {
      v = 0.0;
    }
  
    const currency = new Intl.NumberFormat("ja-JP", {
      style: "currency",
      currency: "JPY",
    }).format(v);
  
    return currency;
  };
  
export default { constant, http,formatCurrency};