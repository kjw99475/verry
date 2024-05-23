async function getReplyList({trade_idx, page, page_size, goLast}) {
    const result = await axios.get(`/trade/replies/list/${trade_idx}`, {params:{page, page_size}});

    if(goLast == "true") {
        const total = result.data.total_count;
        // const lastPage = parseInt(Math.ceil(total/page_size));
        return getReplyList({trade_idx: trade_idx, page: page, page_size: page_size});
    }

    return result.data;
}

async function replyDelete(idx) {
    const response = await axios.delete(`/trade/replies/delete/${idx}`);

    return response.data;
}

async function replyRegist(replyObj) {
    const response = await axios.post(`/trade/replies/regist`, replyObj);

    return response.data;
}