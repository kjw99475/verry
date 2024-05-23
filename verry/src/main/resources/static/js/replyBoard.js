async function getReplyList({idx, page, page_size, goLast}) {
    const result = await axios.get(`/bbs/replies/list/${idx}`, {params:{page, page_size}});

    if(goLast == "true") {
        const total = result.data.total_count;
        // const lastPage = parseInt(Math.ceil(total/page_size));
        return getReplyList({idx: idx, page: page, page_size: page_size});
    }

    return result.data;
}

async function replyDelete(idx) {
    const response = await axios.delete(`/bbs/replies/delete/${idx}`);

    console.log("reply.js : " + response);
    console.log("reply.js : " + response.data);

    return response.data;
}

async function replyRegist(replyObj) {
    const response = await axios.post(`/bbs/replies/regist`, replyObj);

    console.log("reply.js : " + response);
    console.log("reply.js : " + response.data);

    return response.data;
}